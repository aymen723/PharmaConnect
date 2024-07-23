package dz.pharmaconnect.pharmaconnectstockservice.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.MedicalProduct;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInserter {

    private String name;

    private Double price;

    private String description;
    @JsonProperty("image")
    private String picture;


    private Set<String> tags;

    private boolean pres = false;

    private Set<ProductTag> getTagsOf(List<ProductTag> existingTags) {
        if (this.tags == null) return null;

        return existingTags.stream().filter(tag -> this.tags.contains(tag.getName())).collect(Collectors.toSet());
    }

    public MedicalProduct getProduct(List<ProductTag> existingTags) {
        return MedicalProduct.builder()
                .price(this.price)
                .description(this.description)
                .name(this.name)
                .tags(getTagsOf(existingTags))
                .picture(this.picture)
                .prescriptionMedication(this.pres)
                .build();
    }
}
