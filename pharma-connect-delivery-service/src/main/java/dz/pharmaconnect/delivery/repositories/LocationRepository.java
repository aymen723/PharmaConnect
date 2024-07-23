package dz.pharmaconnect.delivery.repositories;

import dz.pharmaconnect.delivery.model.schema.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
