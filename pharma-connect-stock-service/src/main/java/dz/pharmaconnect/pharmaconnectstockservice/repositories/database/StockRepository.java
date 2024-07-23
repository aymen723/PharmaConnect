package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;


import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.StockSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Stock.StockId> {


    @Query("SELECT s FROM  Stock  s " +
            "JOIN  FETCH  s.pharmacy ph " +
            "JOIN  FETCH  ph.location l   " +
            "JOIN  FETCH  s.medicalproduct mp " +
            "JOIN  FETCH  mp.tags mpt " +
            "WHERE s.id = :id")
    Optional<Stock> findOneByIdJoin(Stock.StockId id);

    @Query("SELECT s FROM Stock s " +
            "WHERE s.id.pharmacyId = :pharmacyId")
    Page<Stock> findFiltered(Pageable pageable, Integer pharmacyId);

    @Query(value = "SELECT distinct s FROM Stock s " +
            "JOIN FETCH s.medicalproduct p " +
            "JOIN FETCH s.pharmacy ph " +
            "JOIN FETCH ph.location loc " +
            "JOIN FETCH s.medicalproduct.tags t " +
            "JOIN FETCH t.type tt " +
            "WHERE ph.enabled = true " +
//            "AND  (:#{#filter.payment} IS NULL OR (ph.supportPayment = :#{#filter.payment}   AND s.price != null ) ) " +

            "AND   ( :#{#filter.available} IS NULL OR    ( (s.overridden AND s.overriddenAvailability = :#{#filter.available}) OR (s.overridden = false AND s.available = :#{#filter.available}) ) )" +
            "AND (:#{#filter.tags} IS NULL  OR :#{#filter.tags?.size()} =  (SELECT COUNT (*) FROM MedicalProduct md JOIN md.tags mtg WHERE mtg.id IN (:#{#filter.tags}) AND  md.id = p.id))  " +
            "AND (:#{#filter.productIds} IS NULL  OR s.id.productId IN  (:#{#filter.productIds})  ) " +
            "AND (:#{#filter.pharmacyIds} IS NULL  OR s.id.pharmacyId IN  (:#{#filter.pharmacyIds})  ) " +
            "AND(:#{#filter.search} is null OR p.name ILIKE %:#{#filter.search}%) " +
            "")
    Page<Stock> findAllBySearch(Pageable pageable, StockSearchFilter filter);

//    @Query("SELECT DISTINCT( s.pharmacy.id)    FROM Stock s " +
//            "JOIN  s.medicalproduct p " +
//            "JOIN  s.pharmacy ph " +
//            "JOIN  ph.location loc " +
//            "JOIN  s.medicalproduct.tags t " +
//            "JOIN  t.type tt " +
//            "WHERE ph.enabled = true " +
//            "AND  (:#{#filter.payment} IS NULL OR (ph.supportPayment = :#{#filter.payment}   AND s.price != null ) ) " +
//            "AND ( (s.overridden AND s.overriddenAvailability) OR (s.overridden = false AND s.available) ) " +
//            "AND (:#{#filter.tags} IS NULL  OR :#{#filter.tags?.size()} =  (SELECT COUNT (*) FROM MedicalProduct md JOIN md.tags mtg WHERE mtg.id IN (:#{#filter.tags}) AND  md.id = p.id))  " +
//            "AND (:#{#filter.productIds} IS NULL  OR s.id.productId IN  (:#{#filter.productIds})  ) " +
//            "AND (:#{#filter.pharmacyIds} IS NULL  OR s.id.pharmacyId IN  (:#{#filter.pharmacyIds})  ) " +
//            "AND(:#{#filter.search} is null OR p.name LIKE %:#{#filter.search}%) " +
//            "")
//    List<Integer> findStockPharmacyIdsBySearch(StockSearchFilter filter);


    @Query("SELECT s FROM Stock  s " +
            "WHERE s.id IN (:stockIds)")
    List<Stock> findAllIn(List<Stock.StockId> stockIds);

}
