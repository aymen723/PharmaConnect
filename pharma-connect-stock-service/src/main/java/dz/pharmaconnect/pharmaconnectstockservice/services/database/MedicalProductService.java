package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.ProductSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.MedicalProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalProductService extends Dao<MedicalProduct, Integer> {


    Page<MedicalProduct> getByFilter(Pageable pageable, ProductSearchFilter filter);

}
