package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {


}
