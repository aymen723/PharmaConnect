package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medical_product_details")
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prd_id")
    private Integer id;


    @Column(name = "prd_title")
    private String title;

    @Column(name = "prd_content", columnDefinition = "TEXT")
    private String content;


    @Column(name = "prd_order")
    private Integer order;

    @ManyToOne(targetEntity = MedicalProduct.class)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private MedicalProduct product;


}
