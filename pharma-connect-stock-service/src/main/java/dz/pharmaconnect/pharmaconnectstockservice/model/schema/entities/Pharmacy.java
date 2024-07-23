package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "pharmacy")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pharma_id")
    private Integer id;

    @Column(name = "pharma_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "pharma_payment_support", nullable = false)
    private boolean supportPayment = false;

    @Column(name = "pharma_enabled", nullable = false)
    private boolean enabled = true;


    @Column(name = "pharma_enabled_sync", nullable = false)
    private boolean enabledSync = false;


    @Column(name = "pharma_picture", columnDefinition = "TEXT")
    private String picture;

    @Column(name = "pharma_phone")
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private Long accountId;

    private boolean para;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pharmacy", targetEntity = UpTime.class)
    @JsonIgnore
    private Set<UpTime> upTimes;


}
