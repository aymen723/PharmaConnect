package dz.pharmaconnect.delivery.repositories;

import dz.pharmaconnect.delivery.model.schema.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
