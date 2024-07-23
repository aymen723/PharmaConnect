package dz.pharmaconnect.delivery.controller;


import dz.pharmaconnect.delivery.client.AuthClient;
import dz.pharmaconnect.delivery.client.PaymentClient;
import dz.pharmaconnect.delivery.client.StockClient;
import dz.pharmaconnect.delivery.model.api.client.AccountDto;
import dz.pharmaconnect.delivery.model.api.client.payment.Payment;
import dz.pharmaconnect.delivery.model.api.dto.DeliveryDto;
import dz.pharmaconnect.delivery.model.api.requests.DeliveryUpdateRequest;
import dz.pharmaconnect.delivery.model.dto.client.stock.Order;
import dz.pharmaconnect.delivery.model.schema.entities.Delivery;
import dz.pharmaconnect.delivery.services.Deliveryservice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import dz.pharmaconnect.delivery.model.api.requests.DeliveryCreationRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
@CrossOrigin
public class DeliveryController {
    private final AuthClient authClient;
    private final StockClient stockClient;
    private final PaymentClient paymentClient;
    private final Deliveryservice deliveryService;



    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<DeliveryDto>> getall() {
        return ResponseEntity.ok(List.of());
    }
@GetMapping("/{deliveryId}")
@PreAuthorize("permitAll()")
public ResponseEntity<DeliveryDto> getDelivery(@PathVariable Long deliveryId , @RequestAttribute(required = false) Long userAccountId) {
    var authrority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().getAuthority();
    var delivery = deliveryService.get(deliveryId).orElseThrow(() -> new FetchNotFoundException("delivery", deliveryId));
    if(authrority.equals(AccountDto.AccountRole.CLIENT.name())) {
        if(!delivery.getAccountId().equals(userAccountId)) {
            throw new IllegalStateException();
        }
    }


    return ResponseEntity.ok(DeliveryDto.map(delivery));
}


@PostMapping
@PreAuthorize("permitAll()")
public ResponseEntity<DeliveryDto> createDelivery(@Valid  @RequestBody DeliveryCreationRequest request){

    var account = authClient.getAccountById(request.getAccountId());


    Delivery delivery = deliveryService.create(request);


    return  ResponseEntity.ok(DeliveryDto.map(delivery));
}


@PutMapping
@PreAuthorize("hasAnyAuthority('SERVICE')")
public ResponseEntity<DeliveryDto> updateDelivery(@Valid @RequestBody DeliveryUpdateRequest request){
      var delivery = deliveryService.get(request.getId()).orElseThrow(() -> new FetchNotFoundException("delivery", request.getId()));
      var newDelivery = request.updateDelivery(delivery);
      var savedDelivery =    deliveryService.save(newDelivery);
    return ResponseEntity.ok(DeliveryDto.map(savedDelivery) );
}




}
