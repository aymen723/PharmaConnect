package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {

    Optional<T> get(ID id);

    List<T> getAll();


    /**
     * this method is used to fetch the entity with all of its associated entities in one query (optimisation of queries)
     *
     * @param id the identifier of the entity
     * @return an Optional of the instance identified by the provided identifier
     */
    Optional<T> fetch(ID id);


    T save(T t);

    List<T> saveAll(Iterable<T> iterable);

    T update(T t);

    void delete(ID id);
}
