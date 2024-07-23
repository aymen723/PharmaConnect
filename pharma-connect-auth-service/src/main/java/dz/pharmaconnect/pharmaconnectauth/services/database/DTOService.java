package dz.pharmaconnect.pharmaconnectauth.services.database;

import java.util.List;
import java.util.Optional;

public interface DTOService<T, ID> {

    Optional<T> fetch(ID id);

    Optional<T> get(ID id);

    List<T> getAll();

    T save(T entity);

    T update(T entity);

    void delete(ID id);


}
