package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "product_tag", indexes = @Index(name = "product_tag_name_index", columnList = "tag_name"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTag {

    @Column(name = "tag_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tag_name")
    private String name;

    @Column(name = "tag_description")
    private String description;


    @ManyToOne()
    @JoinColumn(name = "tag_type_id")
    private TagType type;


}
