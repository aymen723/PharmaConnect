package dz.pharmaconnect.pharmaconnectstockservice.model.api.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ProductSearchFilter {
    private Set<Integer> tags;
    private String search;
    private Set<Integer> products;
}
