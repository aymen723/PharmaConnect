package dz.pharmaconnect.pharmaconnectstockservice.model.api.filters;

import dz.pharmaconnect.pharmaconnectstockservice.model.data.LocationApproximation;
import dz.pharmaconnect.pharmaconnectstockservice.utils.SearchParamsValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PharmacySearchFilter {
    private String name;


    private Set<Integer> ids;


    private StockSearchFilter stockFilter;

    private LocationApproximation approx;


    public static PharmacySearchFilter fromParams(String name, String search, Boolean payment, Boolean available, Set<Long> productIds, Set<Integer> tags, Set<Integer> ids, Double x, Double y, Integer range) {
        Coordinate coordinates = SearchParamsValidator.makeCoordinate(x, y);

        ids = SearchParamsValidator.noEmpty(ids);
        productIds = SearchParamsValidator.noEmpty(productIds);
        tags = SearchParamsValidator.noEmpty(tags);
        var approx = new LocationApproximation(range, coordinates, true);
        var stockFilter = StockSearchFilter.fromParams(search, payment, available, productIds, tags, ids);
        return PharmacySearchFilter.builder()
                .name(name)
                .approx(approx)
                .ids(ids)
                .stockFilter(stockFilter)
                .build();
    }

}
