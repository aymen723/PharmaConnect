package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Bookmark;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreateBookmarkRequest {


    private String name;

    private Integer registeredProd;

    private Set<Integer> products;


}
