package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.*;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class OrderCreationRequest {

    @NotNull
    private Long accountId;
    @NotNull
    private Integer pharmacyId;

    @NotNull
    @NotEmpty
    @Valid
    private List<PurchaseRequest> products;


    @Data
    public static class PurchaseRequest {
        @NotNull
        private Integer productId;

        @Null
        private Stock productStock;

        @NotNull
        @Max(5)
        @Min(1)
        private Integer count;


        public Purchase toPurchase(Order order) {
            return Purchase.builder()
                    .purchaseId(new Purchase.PurchaseId(this.getProductStock().getMedicalproduct().getId(), order.getId()))
//                    .order(order)
//                    .product(this.getProductStock().getMedicalproduct())
                    .count(this.getCount())
                    .productPrice(productStock.getPrice())
                    .build();
        }
    }


    public Order toOrder(Pharmacy pharmacy) {
        var price = this.products.stream().map(p -> p.getProductStock().getPrice() * p.getCount()).mapToDouble(f -> f).sum();


        return Order.builder()
                .date(Instant.now())
                .pharmacy(pharmacy)
                .accountId(this.getAccountId())
                .status(OrderStatus.INITIALIZING)
                .price(price)
                .secret(UUID.randomUUID())
                .build();
    }
}
