package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Order;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

@Data
public class OrderUpdateRequest {

    @NotNull
    private Long id;
    private OrderStatus status;
    private Double checkoutPrice;

    private Double deliveryPrice;

    private Long deliveryId;

    private Long paymentId;

    public Order updateOrder(Order order) {
        if (!order.getId().equals(this.id)) throw new IllegalStateException();
        if (this.checkoutPrice != null) {
            order.setCheckoutPrice(this.checkoutPrice);
        }

        if (this.deliveryPrice != null) {
            order.setDeliveryPrice(this.deliveryPrice);
        }

        if (this.deliveryId != null) {
            order.setDeliveryId(this.deliveryId);
        }

        if (this.paymentId != null) {
            order.setPaymentId(this.paymentId);
        }

        if (this.status != null) {
            order.setStatus(this.status);
        }

        return order;
    }
}
