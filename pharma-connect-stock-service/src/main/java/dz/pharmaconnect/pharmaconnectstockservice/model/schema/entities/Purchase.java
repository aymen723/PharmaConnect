package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "purchace")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @EmbeddedId
    private PurchaseId purchaseId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Order.class)
    @MapsId("order_id")
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @JsonIgnoreProperties({"tags"})
    private MedicalProduct product;

    @Column(name = "product_price")
    private Double productPrice;


    @Column(name = "purchace_count")
    private Integer count;


    @Embeddable()
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PurchaseId implements Serializable {
        @Column(name = "product_id")
        private Integer productId;
        @Column(name = "order_id")
        private Long orderId;

    }
}
