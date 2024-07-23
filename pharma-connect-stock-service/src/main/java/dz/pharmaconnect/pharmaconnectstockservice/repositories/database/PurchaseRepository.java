package dz.pharmaconnect.pharmaconnectstockservice.repositories.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Purchase.PurchaseId> {

}