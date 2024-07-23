package dz.pharmaconnect.delivery.model.api.requests;

import dz.pharmaconnect.delivery.model.schema.entities.Delivery;
import dz.pharmaconnect.delivery.model.schema.enums.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryUpdateRequest {


    @NotNull
    private Long id;
    private DeliveryStatus status;






    private Long paymentId;

    public Delivery updateDelivery(Delivery delivery) {
        if (!delivery.getId().equals(this.id)) throw new IllegalStateException();






        if (this.paymentId != null) {
            delivery.setPaymentId(this.paymentId);
        }

        if (this.status != null) {
            delivery.setStatus(this.status);
        }

        return delivery;
    }
}
