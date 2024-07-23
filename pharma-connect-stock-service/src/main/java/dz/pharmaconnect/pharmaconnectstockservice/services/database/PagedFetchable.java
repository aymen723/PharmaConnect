package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.Map;

public interface PagedFetchable<T> {

    Page<T> getByPage(Pageable pageable);

    Page<T> getByPageFiltered(Pageable pageable, @NonNull Map<String, Object> filters);


}
