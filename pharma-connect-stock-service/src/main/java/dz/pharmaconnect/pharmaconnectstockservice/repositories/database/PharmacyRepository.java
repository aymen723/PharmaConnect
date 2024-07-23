package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.PharmacySearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;

import java.util.Optional;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {


    @Query("SELECT  ph FROM Pharmacy ph " +
            "JOIN  FETCH  ph.location loc " +
//            "JOIN FETCH ph.stock s " +

            "WHERE (:#{#filter.name} IS NULL OR ph.name LIKE %:#{#filter.name}%) " +
            "AND (:#{#filter.ids} IS NULL OR ph.id  IN (:#{#filter.ids}) ) " +
            "AND ( :#{#filter.approx != null} = false OR  :#{#filter.approx?.isValid()} = false OR   CAST(ST_DWithin(loc.point  , :#{#filter.approx?.useCenter()} , :#{#filter.approx?.useRange()} ) as boolean)) " +
            "AND (:#{#filter.stockFilter?.productIds} IS NULL OR :#{#filter.stockFilter?.productIds?.size()}  = ( " +
            /* */      "SELECT COUNT   ( smd.id) FROM Stock s " +
            /* */      "JOIN s.pharmacy sph " +
            /* */      "JOIN s.medicalproduct smd " +
            /* */      "WHERE  sph.id = ph.id " +
            /* */      "AND smd.id IN (:#{#filter.stockFilter?.productIds})  " +
            /* */      "AND   ( :#{#filter?.stockFilter.available} IS NULL OR    ( (s.overridden AND s.overriddenAvailability = :#{#filter?.stockFilter.available}) OR (s.overridden = false AND s.available = :#{#filter?.stockFilter.available}) ) )" +

            ")) " +
            "AND   (:#{#filter.stockFilter?.canFilterPharmacies()}  = false OR ph.id IN (" +
            /* */      "SELECT sph.id FROM Stock s " +
            /* */      "JOIN  s.medicalproduct p " +
            /* */      "JOIN  s.pharmacy sph " +
            /* */      "JOIN  s.medicalproduct.tags t " +
            /* */      "JOIN  t.type tt " +
            /* */      "WHERE sph.enabled = true " +
//            /* */      "AND  (:#{#filter?.stockFilter.payment} IS NULL OR (sph.supportPayment = :#{#filter?.stockFilter.payment}   AND s.price != null ) ) " +
            "AND   ( :#{#filter?.stockFilter.available} IS NULL OR    ( (s.overridden AND s.overriddenAvailability = :#{#filter?.stockFilter.available}) OR (s.overridden = false AND s.available = :#{#filter?.stockFilter.available}) ) )" +
            /* */      "AND (:#{#filter?.stockFilter.tags} IS NULL  OR :#{#filter?.stockFilter.tags?.size()} =  (SELECT COUNT (*) FROM MedicalProduct md JOIN md.tags mtg WHERE mtg.id IN (:#{#filter?.stockFilter.tags}) AND  md.id = p.id))  " +
//            /* */      "AND (:#{#filter?.stockFilter.productIds} IS NULL  OR s.id.productId IN  (:#{#filter?.stockFilter.productIds})  ) " +
            /* */      "AND(:#{#filter?.stockFilter.search} is null OR p.name LIKE %:#{#filter?.stockFilter.search}%) " +
            "))" +
            "ORDER BY " +
            "CASE WHEN (:#{#filter.approx != null} = true AND  :#{#filter.approx?.isValid()} = true AND :#{#filter.approx?.orderByDistance} = true) THEN   ST_DISTANCE(loc.point,:#{#filter.approx?.useCenter()})  END ASC " +
            "")
    Page<Pharmacy> findAllFiltered(Pageable pageable, PharmacySearchFilter filter);

    Optional<Pharmacy> findByAccountId(Long accountId);


}
