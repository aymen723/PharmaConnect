package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.TagTypeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_type_id")
    private Integer id;

    @Column(name = "tag_type_name")
    @Enumerated(EnumType.STRING)
    private TagTypeName name;

}
