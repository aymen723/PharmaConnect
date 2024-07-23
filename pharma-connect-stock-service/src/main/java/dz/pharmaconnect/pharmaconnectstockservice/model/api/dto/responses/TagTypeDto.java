package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.TagType;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.TagTypeName;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TagTypeDto {


    private Integer id;


    private TagTypeName name;

    public static TagTypeDto map(TagType type) {
        if (type == null) return null;
        return TagTypeDto.builder()
                .id(type.getId())
                .name(type.getName())
                .build();
    }
}
