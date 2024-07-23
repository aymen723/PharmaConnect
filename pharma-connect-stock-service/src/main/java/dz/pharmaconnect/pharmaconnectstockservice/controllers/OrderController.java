package dz.pharmaconnect.pharmaconnectstockservice.controllers;

import dz.pharmaconnect.pharmaconnectstockservice.clients.AuthClient;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.clients.auth.AccountDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.OrderDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.PharmacyDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.OrderSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.OrderCreationRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.OrderUpdateRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Order;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Stock;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.*;
import dz.pharmaconnect.pharmaconnectstockservice.utils.SearchParamsValidator;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@CrossOrigin()
public class OrderController {

    private final OrderService orderService;
    private final StockService stockService;
    private final PharmacyService pharmacyService;
    private final AuthClient authClient;
    private final PurchaseService purchaseService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('PHARMACY', 'CLIENT' )")
    public ResponseEntity<Page<OrderDto>> getOrdersPage(@RequestAttribute Long userAccountId,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer pageSize,
                                                        @RequestParam(required = false) UUID orderSecret

    ) {
        final Page<Order> orders;
        var authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().toList().get(0).getAuthority();
        Pageable pageable = SearchParamsValidator.paginate(page, pageSize);

        if (authority.equals(AccountDto.AccountRole.CLIENT.name())) {
            orders = orderService.getBySearchFilter(pageable, OrderSearchFilter.builder().accountId(userAccountId).orderSecret(orderSecret).build());
        } else {
            orders = orderService.getBySearchFilter(pageable, OrderSearchFilter.builder().pharmacyAccountId(userAccountId).orderSecret(orderSecret).build());
        }
        return ResponseEntity.ok(orders.map(OrderDto::map));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SERVICE')")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderCreationRequest request) {


        AccountDto account = authClient.getAccount(request.getAccountId());
        Pharmacy pharmacy = pharmacyService.get(request.getPharmacyId()).orElseThrow(() -> new FetchNotFoundException("pharmacy", request.getPharmacyId()));
        if (!account.isActive() || account.isLocked() || account.getRole() != AccountDto.AccountRole.CLIENT)
            throw new IllegalStateException();
        var stock = stockService.getAllIn(request.getProducts().stream().map(p -> new Stock.StockId(p.getProductId(), request.getPharmacyId())).collect(Collectors.toList()));
        if (stock.size() != request.getProducts().size()) throw new IllegalStateException();

        stock.forEach((s) -> {
            if (!s.isPurchasable()) throw new IllegalStateException();
        });


        request.getProducts().forEach(p -> {
            var prodStock = stock.stream().filter(s -> s.getId().getProductId().equals(p.getProductId())).findFirst().get();
            p.setProductStock(prodStock);

        });

        var order = request.toOrder(pharmacy);
        var createdOrder = orderService.save(order);

        var purchases = request.getProducts().stream().map(p -> p.toPurchase(createdOrder)).collect(Collectors.toList());

        var createdPurchases = purchaseService.saveAll(purchases);
        order.setPurchases(createdPurchases);

        return ResponseEntity.ok(OrderDto.map(order));


    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('SERVICE')")
    public ResponseEntity<OrderDto> patchOrder(@Valid @RequestBody OrderUpdateRequest request) {

        var order = orderService.get(request.getId()).orElseThrow(() -> new FetchNotFoundException("order", request.getId()));
        var updatedOrder = orderService.update(request.updateOrder(order));


        return ResponseEntity.ok(OrderDto.map(updatedOrder));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT','PHARMACY' , 'SERVICE')")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId, @RequestAttribute(required = false) Long userAccountId) {
        var order = orderService.fetch(orderId).orElseThrow(() -> new FetchNotFoundException("order", orderId));
        var authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().toList().get(0).getAuthority();

        if (authority.equals(AccountDto.AccountRole.PHARMACY.name())) {
            if (!Objects.equals(order.getPharmacy().getAccountId(), userAccountId)) throw new ForbiddenException();
        }

        if (authority.equals(AccountDto.AccountRole.CLIENT.name())) {
            if (!order.getAccountId().equals(userAccountId)) throw new ForbiddenException();
        }
        return ResponseEntity.ok(OrderDto.map(order));

    }

    @GetMapping("/{orderId}/pharmacy")
    @PreAuthorize("hasAnyAuthority('ADMIN','CLIENT','PHARMACY' , 'SERVICE')")
    public ResponseEntity<PharmacyDto> getOrderPharmacy(@PathVariable Long orderId, @RequestAttribute(required = false) Long userAccountId) {
        var order = orderService.fetch(orderId).orElseThrow(() -> new FetchNotFoundException("order", orderId));
        var authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().toList().get(0).getAuthority();
        System.out.println("auth : " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if (authority.equals(AccountDto.AccountRole.PHARMACY.name())) {
            if (!Objects.equals(order.getPharmacy().getAccountId(), userAccountId)) throw new ForbiddenException();
        }

        if (authority.equals(AccountDto.AccountRole.CLIENT.name())) {
            if (!order.getAccountId().equals(userAccountId)) throw new ForbiddenException();
        }
        return ResponseEntity.ok(PharmacyDto.map(order.getPharmacy()));

    }
}
