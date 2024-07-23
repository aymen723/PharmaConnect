package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "medical_product", indexes = @Index(name = "medical_product_name_index", columnList = "product_name"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "product_price")
    private Double price;

    @Column(name = "product_is_prescription_med", nullable = false)
    private boolean prescriptionMedication = false;

    @Column(name = "product_picture")
    private String picture;

    @Column(name = "product_is_para", nullable = false)
    private boolean para = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag_associations", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<ProductTag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", targetEntity = ProductDetails.class)
    private List<ProductDetails> details;


}
