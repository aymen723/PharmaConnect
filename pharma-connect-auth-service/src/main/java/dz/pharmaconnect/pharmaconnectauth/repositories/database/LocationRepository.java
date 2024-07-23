package dz.pharmaconnect.pharmaconnectauth.repositories.database;

import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {


}
