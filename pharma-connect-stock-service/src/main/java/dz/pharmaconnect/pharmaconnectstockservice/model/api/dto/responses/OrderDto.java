package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Order;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Purchase;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class OrderDto {


    private Long id;


    private UUID secret;


    private Long deliveryId;


    private Long paymentId;


    private Long accountId;


    private PharmacyDto pharmacy;


    private List<PurchaseDto> purchases;


    private Double checkoutPrice;


    private OrderStatus status;


    private Instant date;


    private Double price;


    private Double deliveryPrice;

    public static OrderDto map(Order order) {
        if (order == null) return null;

        return OrderDto.builder()
                .id(order.getId())
                .secret(order.getSecret())
                .pharmacy(PharmacyDto.map(order.getPharmacy()))
                .deliveryId(order.getDeliveryId())
                .paymentId(order.getPaymentId())
                .accountId(order.getAccountId())
                .purchases(PurchaseDto.map(order.getPurchases()))
                .checkoutPrice(order.getCheckoutPrice())
                .status(order.getStatus())
                .date(order.getDate())
                .price(order.getPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .build();
    }

}
