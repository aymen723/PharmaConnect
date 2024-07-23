package dz.pharmaconnect.delivery.model.api.client.payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import dz.pharmaconnect.delivery.model.api.client.payment.Status;

@Data
@Builder
public class Payment {

    private Long paymentId;

    private Long userId;

    private Integer pharmacyId;

    private String invoiceNumber;

    private Instant dueDate;

    private Double Checkoutprice;

    private  String checkouturl;

    // @Column(name = "payment_discount")
    // private Double discount;

    private  Long deliveryId;

    private String comment;




    private Status paymentStatus;

    private String option;

}
