package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import java.util.List;
import java.util.Optional;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Purchase;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.PurchaseRepository;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.Dao;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class BasicPurchaseService implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    // @Override


    @Override
    public Optional<Purchase> get(Purchase.PurchaseId purchaseId) {
        return purchaseRepository.findById(purchaseId);
    }


    @Override
    public Optional<Purchase> fetch(Purchase.PurchaseId purchaseId) {
        return this.get(purchaseId);
    }

    @Override
    public List<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Override
    public Purchase update(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Override
    public void delete(Purchase.PurchaseId purchaseId) {
        purchaseRepository.deleteById(purchaseId);
    }

    @Override
    public List<Purchase> saveAll(Iterable<Purchase> collection) {
        return purchaseRepository.saveAll(collection);
    }
}
