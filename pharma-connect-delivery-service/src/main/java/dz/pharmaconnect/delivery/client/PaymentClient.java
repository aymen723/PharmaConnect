package dz.pharmaconnect.delivery.client;


import dz.pharmaconnect.delivery.client.config.MicroServiceClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import dz.pharmaconnect.delivery.model.api.client.payment.Payment;
@FeignClient(name = "PHARMA-CONNECT-PAYMENT", configuration = MicroServiceClientConfig.class)
public interface PaymentClient {


    @GetMapping("/api/v1/payments/{paymentId}")
    public Payment fetchPayment(@PathVariable Long paymentId);





    }
