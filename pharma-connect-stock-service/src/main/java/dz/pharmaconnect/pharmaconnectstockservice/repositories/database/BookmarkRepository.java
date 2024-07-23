package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.BookmarkSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT  b FROM  Bookmark b " +
//            "JOIN FETCH b.products pds " +
//            "JOIN  FETCH b.registeredProduct rp " +

            "WHERE (1=1)" +
            "AND (:#{#filter.accountId} IS NULL OR b.accountId = :#{#filter.accountId}) " +
            "AND (:#{#filter.name} IS NULL OR b.name  ILIKE %:#{#filter.name}% )" +
            "AND (:#{#filter.registeredProd} IS NULL OR b.registeredProduct.id =  :#{#filter.registeredProd})" +
            "ORDER BY b.createDate DESC " +
            "")
    Page<Bookmark> fetchByFilter(Pageable pageable, BookmarkSearchFilter filter);

    @Query("SELECT b FROM Bookmark  b " +
            "JOIN FETCH b.products pds " +
            "JOIN  FETCH b.registeredProduct rp " +
            "WHERE b.id = :id")
    Optional<Bookmark> fetchById(Long id);

}
