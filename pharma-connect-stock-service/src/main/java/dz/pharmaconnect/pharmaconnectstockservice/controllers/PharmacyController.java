package dz.pharmaconnect.pharmaconnectstockservice.controllers;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dz.pharmaconnect.pharmaconnectstockservice.clients.AuthClient;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.clients.auth.AccountDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.PharmacyDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.PharmacySearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.SyncStockRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.UpdatePharmacyRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.UpdateStockRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.UpdateUptimesRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.responses.StockResp;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.responses.PharmacyUpTime;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Stock;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.UpTime;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.UpTimeRepository;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.PharmacyService;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.StockService;
import dz.pharmaconnect.pharmaconnectstockservice.utils.SearchParamsValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;
    private final StockService stockService;
    private final AuthClient authClient;
    private final UpTimeRepository upTimeRepository;

    @GetMapping()
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<PharmacyDto>> GetAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1000") Integer range,
            @RequestParam(required = false) Double x,
            @RequestParam(required = false) Double y,
            @RequestParam(required = false) Set<Integer> tags,
            @RequestParam(required = false) Set<Long> products,
            @RequestParam(required = false) Set<Integer> ids,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean payment,
            @RequestParam(required = false) Boolean available


    ) {


        var filter = PharmacySearchFilter.fromParams(name, search, payment, available, products, tags, ids, x, y, range);
        final var authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst();
        final String role;
        if (authority.isPresent() && !authority.get().getAuthority().equals("ROLE_ANONYMOUS")) {
            role = authority.get().getAuthority();
        } else {
            role = "VISITOR";

        }

        if (role.equals("VISITOR") || role.equals("CLIENT")) {
            System.out.println("SETTING AVAILABLE FOR PHARMACIES");
            filter.getStockFilter().setAvailable(true);
        }
        System.out.println("ROLE IS :: " + role);
        System.out.println("pharma filter  = " + filter);
        Pageable pageable = SearchParamsValidator.paginate(page, pageSize);
        var pharmacyData = pharmacyService.getAllFiltered(pageable, filter);
        return ResponseEntity.status(HttpStatus.OK).body(pharmacyData.map(PharmacyDto::map));
    }


    @GetMapping("/{pharmacyId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PharmacyDto> getOneById(@PathVariable Long pharmacyId, @RequestParam(required = false, defaultValue = "false") boolean isAccountId) {
        final Pharmacy pharmacy;
        if (isAccountId) {
            pharmacy = pharmacyService.getByAccountId(pharmacyId).orElseThrow(() -> new FetchNotFoundException("pharmacy (accountId)", pharmacyId));
        } else {
            pharmacy = pharmacyService.fetch(pharmacyId.intValue()).orElseThrow(() -> new FetchNotFoundException("pharmacy", pharmacyId));
        }


        return ResponseEntity.ok(PharmacyDto.map(pharmacy));
    }

    @GetMapping("/{pharmacyId}/stock")
    @PreAuthorize("permitAll()")
    public void getPharmacyStockByPharmacyId(
            @PathVariable Integer pharmacyId,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse,
            RedirectAttributes attributes) throws ServletException, IOException {
        httpRequest.setAttribute("pharmacyId", pharmacyId);
        httpRequest.getRequestDispatcher("/api/v1/stock").forward(httpRequest, httpResponse);
    }

    @PutMapping("/{pharmacyId}/stock/{productId}")
    @PreAuthorize("hasAnyAuthority('PHARMACY')")
    public ResponseEntity<StockResp> updateStock(@PathVariable Integer pharmacyId, @PathVariable Integer productId, @RequestAttribute Long userAccountId, @RequestBody UpdateStockRequest request) {
        Pharmacy pharmacy = pharmacyService.get(pharmacyId).orElseThrow(() -> new FetchNotFoundException("pharmacy", pharmacyId));
        if (!userAccountId.equals(pharmacy.getAccountId())) {
            throw new ForbiddenException();
        }
        var stockId = new Stock.StockId(productId, pharmacyId);
        Stock stock = stockService.get(stockId).orElseThrow(() -> new FetchNotFoundException("stock", stockId));

        System.out.println("update request ::: " + request);
//
        if (request.getOverridden() != null) {
            stock.setOverridden(request.getOverridden());
        }


        if (request.getOverriddenAvailability() != null) {
            stock.setOverriddenAvailability(request.getOverriddenAvailability());
        }

        if (request.getPrice() != null) {
            stock.setPrice(request.getPrice());
        }
        stockService.update(stock);
//
        Stock newStock = stockService.fetch(stockId).orElseThrow(() -> new FetchNotFoundException("stock", stockId));

        return ResponseEntity.ok(StockResp.from(newStock, true));


    }

    @PatchMapping
    @PreAuthorize("hasAuthority('PHARMACY')")
    public ResponseEntity<PharmacyDto> patchPharmacy(@RequestAttribute long userAccountId, @Valid @RequestBody UpdatePharmacyRequest request) {
        var pharmacy = pharmacyService.getByAccountId(userAccountId).orElseThrow(() -> new FetchNotFoundException("(acc) pharmacy", userAccountId));
        if (request.getNewPhoneNumber() != null) {
            pharmacy.setPhoneNumber(request.getNewPhoneNumber());
        }

        if (request.getNewName() != null) {
            pharmacy.setName(request.getNewName());
        }

        var newPharma = pharmacyService.save(pharmacy);


        return ResponseEntity.ok(PharmacyDto.map(newPharma));
    }

    @PatchMapping("/{pharmacyId}/stock/sync")
    @PreAuthorize("hasAuthority('PHARMACY')")
    public ResponseEntity<Void> syncStock(@RequestBody SyncStockRequest request, @RequestAttribute Long userAccountId
    ) {
        var pharmacy = pharmacyService.getByAccountId(userAccountId).orElseThrow(() -> new FetchNotFoundException("(acc) pharmacy", userAccountId));
        stockService.syncStockState(request, pharmacy.getId());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{pharmacyId}/uptimes")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PharmacyUpTime> getUpTimes(@PathVariable Integer pharmacyId) {
        var pharmacy = pharmacyService.get(pharmacyId).orElseThrow(() -> new FetchNotFoundException("Pharmacy", pharmacyId));
        System.out.println("uptimes pharmacy :::: " + pharmacy);
        return ResponseEntity.ok(PharmacyUpTime.from(pharmacy.getUpTimes()));
    }


    @PutMapping("/{pharmacyId}/uptimes")
    @PreAuthorize("hasAnyAuthority('PHARMACY')")
    public ResponseEntity<PharmacyUpTime> updateUptimes(@RequestBody @Valid List<UpdateUptimesRequest> updateUptimes, @PathVariable Integer pharmacyId, @RequestAttribute Long userAccountId) {
        var pharmacy = pharmacyService.get(pharmacyId).orElseThrow(() -> new FetchNotFoundException("pharmacy", pharmacyId));

        if (!pharmacy.getAccountId().equals(userAccountId)) {
            throw new IllegalStateException();
        }


        var days = DayOfWeek.values();
        List<UpTime> newUptimes = new ArrayList<>();
        for (final var day : days) {
            var uptimeOpt = pharmacy.getUpTimes().stream().filter(up -> up.getDay().equals(day)).findFirst();
            var match = updateUptimes.stream().filter(up -> up.getDay().equals(day)).findFirst().orElseThrow(IllegalStateException::new);

            final var uptime = uptimeOpt.orElseGet(() -> UpTime.builder()
                    .pharmacy(pharmacy)
                    .day(day)
                    .openTime(match.getOpenTime())
                    .closeTime(match.getCloseTime())
                    .working(true)
                    .build());
            uptime.setOpenTime(match.getOpenTime());
            uptime.setCloseTime(match.getCloseTime());
            newUptimes.add(uptime);
        }

        newUptimes = upTimeRepository.saveAll(newUptimes);

        return ResponseEntity.ok(PharmacyUpTime.from(new HashSet<>(newUptimes)));
    }

    @GetMapping("accounts")
    public ResponseEntity<List<AccountDto>> getAccounts() {
        return ResponseEntity.ok(authClient.getAccounts());
    }


}
