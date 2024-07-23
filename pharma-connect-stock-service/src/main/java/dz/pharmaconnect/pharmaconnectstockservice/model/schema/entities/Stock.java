package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "stock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Stock {

    @EmbeddedId
    private StockId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pharma_id")
    @JoinColumn(name = "pharma_id", referencedColumnName = "pharma_id")
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("product_id")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private MedicalProduct medicalproduct;

    @Column(name = "stock_is_available")
    private boolean available = true;

    @Column(name = "stock_product_price")
    private Double price;

    @Column(name = "stock_overridden")
    private boolean overridden;

    @Column(name = "stock_overridden_availability")
    private boolean overriddenAvailability;

    @Column(name = "stock_max_purchase_count")
    private Integer maxPurchaseCount;


    @Column(name = "stock_update_identifier")
    private UUID updateIdentifier;

    @Column(name = "stock_is_selling", nullable = false)
    private boolean selling = true;


    @JsonIgnore
    public boolean isPurchasable() {
        var available = (this.isOverridden() && this.isOverriddenAvailability()) || this.isAvailable();
        return available && this.isSelling() && this.getPharmacy().isEnabled() && this.getPharmacy().isSupportPayment() && !this.getMedicalproduct().isPrescriptionMedication() && this.getPrice() != null;
    }


    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StockId implements Serializable {
        @Column(name = "product_id")
        private Integer productId;
        @Column(name = "pharma_id")
        private Integer pharmacyId;
    }


}
