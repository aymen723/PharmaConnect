package dz.pharmaconnect.pharmaconnectstockservice.controllers;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.StockSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.responses.StockResp;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Stock;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.StockService;
import dz.pharmaconnect.pharmaconnectstockservice.utils.SearchParamsValidator;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;


    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<?>> getApproximateStock(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,

            @RequestParam(required = false) Set<Integer> tags,
            @RequestParam(required = false) Set<Long> products,
            @RequestParam(required = false) Set<Integer> pharmacies,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean payment,
            @RequestParam(required = false) Boolean available,
            @RequestAttribute(required = false) Integer pharmacyId,
            @RequestAttribute(required = false) Long userAccountId


    ) {

        if (pharmacyId != null) {
            if (pharmacies == null) {
                pharmacies = new HashSet<>();
            }
            pharmacies.add(pharmacyId);
        }

        var filter = StockSearchFilter.fromParams(search, payment, available, products, tags, pharmacies);
        Pageable pageable = SearchParamsValidator.paginate(page, pageSize);
        final var authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst();
        final String role;
        if (authority.isPresent() && !authority.get().getAuthority().equals("ROLE_ANONYMOUS")) {
            role = authority.get().getAuthority();
        } else {
            role = "VISITOR";

        }

        if (role.equals("VISITOR") || role.equals("CLIENT")) {
//            System.out.println("SETTING AVAILABLE");
            filter.setAvailable(true);
        }
//        System.out.println("ROLE IS :::: " + role);
//        System.out.println("stock filter :: " + filter);
        var stock = stockService.getApproximateBySearch(pageable, filter);
        var isPrivate = (role.equals("PHARMACY") || role.equals("ADMIN"));
        var availableStock = stock.map(s -> StockResp.from(s, isPrivate));
        return ResponseEntity.ok(availableStock);
    }

    @GetMapping("/{pharmacyId}/{productId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Stock> getStock(@PathVariable Integer pharmacyId, @PathVariable Integer productId) {

        var stock = stockService.fetch(new Stock.StockId(productId, pharmacyId)).orElseThrow(() -> new FetchNotFoundException("stock", new Stock.StockId(productId, pharmacyId)));
        return ResponseEntity.ok(stock);
    }


    @PostMapping()
    @PermitAll
    public ResponseEntity<String> setStock() {
        return ResponseEntity.ok("got through stock");
    }


}