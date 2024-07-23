package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Stock;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StockDto {


    private Stock.StockId id;


    private PharmacyDto pharmacy;


    private MedicalProductDto medicalproduct;


    private boolean available = true;


    private Double price;


    private boolean overridden;


    private boolean overriddenAvailability;


    private Integer maxPurchaseCount;


    private boolean selling = true;

    private boolean purchasable;

    public static StockDto map(Stock stock) {
        if (stock == null) return null;

        return StockDto.builder()
                .id(stock.getId())
                .pharmacy(PharmacyDto.map(stock.getPharmacy()))
                .medicalproduct(MedicalProductDto.map(stock.getMedicalproduct()))
                .available(stock.isAvailable())
                .price(stock.getPrice())
                .overridden(stock.isOverridden())
                .overriddenAvailability(stock.isOverriddenAvailability())
                .maxPurchaseCount(stock.getMaxPurchaseCount())
                .selling(stock.isSelling())
                .purchasable(stock.isPurchasable())
                .build();

    }
}
