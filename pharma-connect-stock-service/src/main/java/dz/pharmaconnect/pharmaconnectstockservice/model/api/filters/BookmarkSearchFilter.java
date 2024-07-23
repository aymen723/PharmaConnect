package dz.pharmaconnect.pharmaconnectstockservice.model.api.filters;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookmarkSearchFilter {

    private Long accountId;
    private String name;
    private Integer registeredProd;

}
