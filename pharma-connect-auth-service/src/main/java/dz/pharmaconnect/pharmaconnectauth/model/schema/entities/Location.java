package dz.pharmaconnect.pharmaconnectauth.model.schema.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "location_latitude")
    private Double latitude;
    @Column(name = "location_longitude")
    private Double longitude;
    @Column(name = "location_address")
    private String address;

    @Column(name = "location_ph")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;
}
