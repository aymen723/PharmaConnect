package dz.pharmaconnect.pharmaconnectstockservice.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.ProductTag;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.TagType;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.TagTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagInserter {

    private String name;
    private TagTypeName type;

    private String description;

    public ProductTag getTag(List<TagType> types) {
        TagType type = types.stream().filter(t -> t.getName().equals(this.type)).findFirst().orElseThrow(() -> new RuntimeException("Type of Tag Not Found :: " + this));
       return ProductTag.builder()
                .name(this.name)
                .description(this.description)
                .type(type)
                .build();
    }


}
