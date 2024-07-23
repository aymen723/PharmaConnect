package dz.pharmaconnect.delivery.model.api.requests;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeliveryCreationRequest {


    @NotNull
    private Long accountId;

/*    @Column(name = "delivery_encode" , nullable = false)
    private UUID encode;*/

    @NotNull
    private Long orderId;


    private Long paymentId;

    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;


}
