package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.UpTime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UpTimeRepository extends JpaRepository<UpTime, Long> {


}
