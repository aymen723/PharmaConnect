package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "bookmark")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @Column(name = "bookmark_account_id", nullable = false)
    private Long accountId;

    @Column(name = "bookmark_name")
    private String name;

    @Column(name = "bookmark_create_date")
    private Instant createDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = MedicalProduct.class)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"tags"})
    private MedicalProduct registeredProduct;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bookmarked_product", joinColumns = @JoinColumn(name = "bookmark_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonIgnoreProperties({"tags"})
    private List<MedicalProduct> products;
}
