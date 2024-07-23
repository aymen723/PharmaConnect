package dz.pharmaconnect.delivery.client;

import dz.pharmaconnect.delivery.client.config.MicroServiceClientConfig;
import dz.pharmaconnect.delivery.model.dto.client.stock.Order;
import dz.pharmaconnect.delivery.model.dto.client.stock.Pharmacy;
/*
import dz.pharmaconnect.delivery.model.schema.api.requests.OrderUpdateRequest;
*/
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PHARMA-CONNECT-STOCK-SERVICE", configuration = MicroServiceClientConfig.class)
public interface StockClient {


    @GetMapping("/api/v1/orders/{orderId}")
    Order getOrderById(@PathVariable Long orderId);

    @GetMapping("/api/v1/orders/{orderId}/pharmacy")
    Pharmacy getPharmacyByOrderId(@PathVariable Long orderId);



/*    @PutMapping("/api/v1/orders")
    Order patchOrder(@RequestBody OrderUpdateRequest order);*/


}
