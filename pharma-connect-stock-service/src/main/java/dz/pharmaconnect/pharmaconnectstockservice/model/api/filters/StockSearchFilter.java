package dz.pharmaconnect.pharmaconnectstockservice.model.api.filters;

import dz.pharmaconnect.pharmaconnectstockservice.utils.SearchParamsValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockSearchFilter {


    private Set<Integer> tags;
    private String search;
    private Set<Long> productIds;
    private Set<Integer> pharmacyIds;
    private Boolean payment;
    private Boolean available;


    public boolean canFilterPharmacies() {
        return productIds == null && (tags != null || search != null || payment != null || available != null);
    }


    public static StockSearchFilter fromParams(String search, Boolean payment, Boolean available, Set<Long> productIds, Set<Integer> tags, Set<Integer> pharmacyIds) {

        pharmacyIds = SearchParamsValidator.noEmpty(pharmacyIds);
        productIds = SearchParamsValidator.noEmpty(productIds);
        tags = SearchParamsValidator.noEmpty(tags);
        return StockSearchFilter.builder()
                .search(search)
                .payment(payment)
                .productIds(productIds)
                .tags(tags)
                .pharmacyIds(pharmacyIds)
                .available(available)
                .build();
    }


}
