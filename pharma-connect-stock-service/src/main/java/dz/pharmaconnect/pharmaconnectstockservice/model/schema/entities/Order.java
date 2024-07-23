package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchace_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;


    @Column(name = "order_secret")
    private UUID secret;


    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "order_account_id", nullable = false)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharma_id", referencedColumnName = "pharma_id", nullable = false)
    private Pharmacy pharmacy;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", targetEntity = Purchase.class)
    private List<Purchase> purchases;

    @Column(name = "order_checkout_price")
    private Double checkoutPrice;


    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)

    private OrderStatus status;

    @Column(name = "order_date", nullable = false)
    private Instant date;

    @Column(name = "order_price", nullable = false)
    private Double price;

    @Column(name = "order_delivery_price")
    private Double deliveryPrice;

}
