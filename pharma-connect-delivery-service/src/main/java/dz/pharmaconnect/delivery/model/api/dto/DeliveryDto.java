package dz.pharmaconnect.delivery.model.api.dto;

import dz.pharmaconnect.delivery.model.schema.entities.Delivery;
import dz.pharmaconnect.delivery.model.schema.entities.Location;
import dz.pharmaconnect.delivery.model.schema.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class DeliveryDto {


    private Long id;


    private UUID encode;


    private Long orderId;


    private Long paymentId;


    private Instant creationDate;


    private Instant deliveredDate;



    private DeliveryStatus status;


    private LocationDto location;

    public static DeliveryDto map(Delivery delivery) {
        if(delivery == null)
            return null;

        return DeliveryDto.builder()
                .id(delivery.getId())
                .encode(delivery.getEncode())
                .orderId(delivery.getOrderId())
                .paymentId(delivery.getPaymentId())
                .creationDate((delivery.getCreationDate()))
                .deliveredDate(delivery.getDeliveredDate())
                .status(delivery.getStatus())
                .location(LocationDto.map(delivery.getLocation()))
                .build();
    }
}
