package dz.pharmaconnect.delivery.model.schema.entities;

import dz.pharmaconnect.delivery.model.schema.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@ToString()
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @Column(name = "delivery_encode" , nullable = false)
    private UUID encode;

    @Column(name = "delivery_order_id", nullable = false)
    private Long orderId;

    @Column(name = "delivery_payment_id")
    private Long paymentId;

    @Column(name = "delivery_creation_date")
    private Instant creationDate;

    @Column(name = "delivery_delivered_date")
    private Instant deliveredDate;


    @Column(name = "delivery_account_id")
    private Long accountId;

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")
    private Location location;




}
