package dz.pharmaconnect.pharmaconnectstockservice.model.api.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagSearchFilter {

    private String name;
    private List<Integer> tags;


}
