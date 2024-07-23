package dz.pharmaconnect.delivery.model.schema.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Location {



    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;
    @Column(name = "location_latitude")
    private Double latitude;
    @Column(name = "location_longitude")
    private Double longitude;
    @Column(name = "location_address")
    private String address;


    @OneToOne(fetch = FetchType.LAZY , targetEntity = Delivery.class)
    @JoinColumn(name = "location_delivery_id" , referencedColumnName = "delivery_id")
    private Delivery delivery;
}
