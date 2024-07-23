package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.ProductSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.MedicalProduct;

import java.util.Optional;

@Repository
public interface MedicalProductRepository extends JpaRepository<MedicalProduct, Integer> {

    @Query("SELECT s from MedicalProduct s " +
            "JOIN FETCH s.tags tg " +
            "JOIN FETCH tg.type tt " +
            "WHERE  s.id = :productId")
    Optional<MedicalProduct> findOneByIdJoin(Integer productId);

    @Query(value = "SELECT  s from MedicalProduct s " +
            "JOIN FETCH s.tags tg " +
            "JOIN FETCH tg.type tt " +
            "WHERE (:#{#filter.tags} IS NULL  OR :#{#filter.tags?.size()} =  (SELECT COUNT (*) FROM MedicalProduct  md JOIN md.tags mtg WHERE mtg.id IN (:#{#filter.tags}) AND md.id = s.id) ) " +
            "AND (:#{#filter.products} IS NULL OR s.id IN (:#{#filter.products})) " +
            "AND (:#{#filter.search} IS NULL OR s.name ILIKE %:#{#filter.search}%)"
            , countQuery = "SELECT COUNT(DISTINCT s) from MedicalProduct s " +
            "JOIN  s.tags tg " +
            "JOIN  tg.type tt " +
            "WHERE (:#{#filter.tags} IS NULL  OR :#{#filter.tags?.size()} =  (SELECT COUNT (*) FROM MedicalProduct  md JOIN md.tags mtg WHERE mtg.id IN (:#{#filter.tags}) AND md.id = s.id) ) " +
            "AND (:#{#filter.products} IS NULL OR s.id IN (:#{#filter.products})) " +
            "AND (:#{#filter.search} IS NULL OR s.name ILIKE %:#{#filter.search}%)" +
            "")
//            "AND  (:tags IS NULL OR  tg.id IN (:tags))")
    Page<MedicalProduct> findMedicalProductsByFilter(Pageable pageable, ProductSearchFilter filter);


}