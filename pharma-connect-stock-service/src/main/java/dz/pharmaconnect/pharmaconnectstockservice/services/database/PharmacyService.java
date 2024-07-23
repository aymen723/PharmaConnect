package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.PharmacySearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PharmacyService extends Dao<Pharmacy, Integer> {


    Page<Pharmacy> getAllFiltered(Pageable pageable, PharmacySearchFilter filter);

    Optional<Pharmacy> getByAccountId(Long accountId);
}
