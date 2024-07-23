package dz.pharmaconnect.pharmaconnectstockservice.model.api.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.MedicalProductDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.PharmacyDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Stock;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResp {
    private Stock.StockId id;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MedicalProductDto product;
    private PharmacyDto pharmacy;
    private Double price;
    private PrivateData privateData;
    private boolean purchasable = false;

    public static StockResp from(Stock stock, boolean addPrivate) {

        var s = StockResp.builder()
                .id(stock.getId())
                .product(MedicalProductDto.map(stock.getMedicalproduct()))
                .pharmacy(PharmacyDto.map(stock.getPharmacy()))
                .price(stock.getPrice())
                .purchasable(stock.isPurchasable())
                .build();

        if (addPrivate) {
            s.setPrivateData(PrivateData.builder()
                    .available(stock.isAvailable())
                    .overridden(stock.isOverridden())
                    .overriddenAvailability(stock.isOverriddenAvailability())
                    .build());
        }

        return s;
    }


    @Data
    @Builder
    public static class PrivateData {
        private boolean available;
        private boolean overridden;
        private boolean overriddenAvailability;
    }
}
