package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Purchase;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class PurchaseDto {


    @EmbeddedId
    private Purchase.PurchaseId purchaseId;


    @JsonIgnoreProperties({"tags"})
    private MedicalProductDto product;

    @Column(name = "product_price")
    private Double productPrice;


    @Column(name = "purchace_count")
    private Integer count;


    public static PurchaseDto map(Purchase purchase) {
        if (purchase == null) return null;
        return PurchaseDto.builder()
                .purchaseId(purchase.getPurchaseId())
                .product(MedicalProductDto.map(purchase.getProduct(), false))
                .productPrice(purchase.getProductPrice())
                .count(purchase.getCount())
                .build();
    }

    public static Set<PurchaseDto> map(Set<Purchase> purchases) {
        if (purchases == null) return null;
        return purchases.stream().map(PurchaseDto::map).collect(Collectors.toSet());
    }

    public static List<PurchaseDto> map(List<Purchase> purchases) {
        if (purchases == null) return null;
        return purchases.stream().map(PurchaseDto::map).collect(Collectors.toList());
    }


}
