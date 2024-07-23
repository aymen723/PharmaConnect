package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.TagSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.ProductTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductTagService extends Dao<ProductTag, Integer> {

    Page<ProductTag> getAllFiltered(Pageable pageable, TagSearchFilter filter);
}
