package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.TagSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.ProductTag;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {


    @Query("SELECT t FROM  ProductTag t " +
            "JOIN FETCH t.type tp " +
            "WHERE (:#{#filter.name} IS NULL OR t.name LIKE %:#{#filter.name}%) " +
            "AND (:#{#filter.tags} IS NULL OR t.id IN (:#{#filter.tags}))" +
            "ORDER BY CASE WHEN :#{#filter.name != null} = true AND t.name LIKE :#{#filter.name}%  THEN 1 ELSE 2 END")
    Page<ProductTag> findProductTagsByFilter(Pageable pageable, TagSearchFilter filter);
}