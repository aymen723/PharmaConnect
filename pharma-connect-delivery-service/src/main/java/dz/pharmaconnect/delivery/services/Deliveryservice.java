package dz.pharmaconnect.delivery.services;


import dz.pharmaconnect.delivery.model.api.client.payment.Payment;
import dz.pharmaconnect.delivery.model.api.requests.DeliveryCreationRequest;
import dz.pharmaconnect.delivery.model.dto.client.stock.Order;
import dz.pharmaconnect.delivery.model.schema.entities.Delivery;
import dz.pharmaconnect.delivery.model.schema.entities.Location;
import dz.pharmaconnect.delivery.model.schema.enums.DeliveryStatus;
import dz.pharmaconnect.delivery.repositories.DeliveryRepository;
import dz.pharmaconnect.delivery.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Deliveryservice {

    private final  DeliveryRepository deliveryrepo;

    private final LocationRepository locationRepository;

    public Optional<Delivery> get(Long ID){
        return deliveryrepo.findById(ID);
    }


    public List<Delivery> fetchall(){
        return deliveryrepo.findAll();
    }

    public Delivery create(DeliveryCreationRequest  request){

        Instant date = Instant.now();


        var delivery = deliveryrepo.save( Delivery.builder()
                .creationDate(date)
                .orderId(request.getOrderId())
                .accountId(request.getAccountId())
                .paymentId(request.getPaymentId())
                .status(DeliveryStatus.INITIALIZING)
                .encode(UUID.randomUUID())

                .build());
        var location = Location.builder().delivery(delivery).latitude(request.getLatitude()).longitude(request.getLongitude()).build();



        return deliveryrepo.save(delivery);
    }

    public Delivery save(Delivery delivery) {
        return deliveryrepo.save(delivery);
    }

    public void delete(Long ID){
        deliveryrepo.deleteById(ID);

    }
}

