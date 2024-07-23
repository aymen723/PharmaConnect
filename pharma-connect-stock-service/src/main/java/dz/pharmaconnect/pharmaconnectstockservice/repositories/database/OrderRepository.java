package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.OrderSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByAccountId(Long accountId);


    @Query(value = "SELECT o FROM Order o " +
            "JOIN FETCH o.pharmacy ph " +
            "JOIN FETCH o.purchases pur " +
            "WHERE o.id = :orderId ")
    Optional<Order> fetchOneById(Long orderId);

    @Query(value = "SELECT o FROM Order o " +
            "JOIN FETCH o.pharmacy ph " +
            "JOIN FETCH o.purchases pur " +
            "WHERE (1=1) " +
            "AND (:#{#filter.accountId} is null OR o.accountId = :#{#filter.accountId}) " +
            "AND (:#{#filter.orderSecret} is null OR o.secret = :#{#filter.orderSecret}) " +
            "AND (:#{#filter.pharmacyId} is null OR ph.id = :#{#filter.pharmacyId})" +
            "AND (:#{#filter.pharmacyAccountId} is null OR ph.accountId = :#{#filter.pharmacyAccountId}) " +
            "ORDER BY o.date DESC ", countQuery = "select COUNT(distinct o) FROM Order o " +
            "JOIN o.pharmacy ph " +
            "JOIN  o.purchases pur " +
            "WHERE (1=1) " +
            "AND (:#{#filter.accountId} is null OR o.accountId = :#{#filter.accountId}) " +
            "AND (:#{#filter.orderSecret} is null OR o.secret = :#{#filter.orderSecret}) " +
            "AND (:#{#filter.pharmacyId} is null OR ph.id = :#{#filter.pharmacyId})" +
            "AND (:#{#filter.pharmacyAccountId} is null OR ph.accountId = :#{#filter.pharmacyAccountId}) ")
    Page<Order> findBySearchFilter(Pageable pageable, OrderSearchFilter filter);
}