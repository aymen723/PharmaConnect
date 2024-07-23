package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.ProductTag;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.TagType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class ProductTagDto {


    private Integer id;


    private String name;


    private String description;


    private TagTypeDto type;


    public static ProductTagDto map(ProductTag tag) {
        if (tag == null) return null;

        return ProductTagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .description(tag.getDescription())
                .type(TagTypeDto.map(tag.getType()))
                .build();
    }


    public static Set<ProductTagDto> map(Set<ProductTag> tags) {
        if (tags == null) return null;

        return tags.stream().map(ProductTagDto::map).collect(Collectors.toSet());
    }

}
