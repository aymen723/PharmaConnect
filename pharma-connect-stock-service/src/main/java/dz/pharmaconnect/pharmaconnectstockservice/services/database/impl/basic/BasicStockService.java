package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.StockSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.SyncStockRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Stock;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.StockRepository;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.StockService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
@RequiredArgsConstructor
@Transactional
public class BasicStockService implements StockService {


    @PersistenceContext
    private final EntityManager entityManager;

    private final StockRepository stockRepository;


    @Override
    public int syncStockState(SyncStockRequest request, Integer pharmacyId) {
        if (request.getAvailableProds() == null || request.getAvailableProds().isEmpty())
            throw new IllegalStateException();
        var availableProds = request.getAvailableProds().stream().toList();
        UUID newUpdateIdentifier = UUID.randomUUID();

        StringBuilder queryString = new StringBuilder();
        queryString.append("UPDATE stock ");
        queryString.append("SET stock_is_available = data.isAvailable ");
        queryString.append(", stock_update_identifier = :updateIdentifier ");
        queryString.append("FROM ( VALUES ");

        for (var i = 0; i < availableProds.size(); i++) {
            queryString.append("(").append(availableProds.get(i)).append(",");
            queryString.append(true).append(") ");
            if (availableProds.size() != 1 && i != (availableProds.size() - 1)) {
                queryString.append(",");
            }
        }
        queryString.append(") ");
        queryString.append("AS data( productId , isAvailable ) ");
        queryString.append("WHERE pharma_id = :pharmaId AND product_id = data.productId ");


        var query = entityManager.createNativeQuery(queryString.toString());


        query.setParameter("pharmaId", pharmacyId);
        query.setParameter("updateIdentifier", newUpdateIdentifier);
//        for (var obj : dataList) {
//            query.setParameter(obj.getProductId() + "productId", obj.getProductId());
//            query.setParameter(obj.getProductId() + "isAvailable", obj.isAvailable());
//            query.setParameter(obj.getProductId() + "isCritical", obj.isCritical());
//        }
        int updatedRowCount = query.executeUpdate();
        int resetRowCount = 0;
        if (request.isPropagate()) {
            resetRowCount = this.autocorrectUnidentifiedStock(newUpdateIdentifier, pharmacyId);
        }
        ;

        return updatedRowCount + resetRowCount;
    }


    @Override
    public int autocorrectUnidentifiedStock(UUID updateIdentifier, Integer pharmacyId) {
        String queryString = "UPDATE stock " +
                "SET stock_is_available = false " +
                "WHERE pharma_id = :pharmacyId " +
                "AND (stock_update_identifier != :updateIdentifier " +
                "OR stock_update_identifier is null)";


        var query = entityManager.createNativeQuery(queryString);
        query.setParameter("pharmacyId", pharmacyId);
        query.setParameter("updateIdentifier", updateIdentifier);
        return query.executeUpdate();

    }

    @Override
    public Optional<Stock> get(Stock.StockId stockId) {
        return stockRepository.findById(stockId);
    }


    @Override
    public Optional<Stock> fetch(Stock.StockId stockId) {
        return stockRepository.findOneByIdJoin(stockId);
    }

    @Override
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock update(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void delete(Stock.StockId stockId) {
        stockRepository.deleteById(stockId);
    }

    @Override
    public List<Stock> saveAll(Iterable<Stock> collection) {
        return stockRepository.saveAll(collection);
    }

    @Override
    public Page<Stock> getByPage(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

    @Override
    public Page<Stock> getByPageFiltered(Pageable pageable, @NonNull Map<String, Object> filters) {
        return stockRepository.findFiltered(pageable, null);
    }

    @Override
    public Page<Stock> getApproximateBySearch(Pageable pageable, StockSearchFilter filter) {
        System.out.println("filter is : " + filter);
        return stockRepository.findAllBySearch(pageable, filter);
    }


    @Override
    public List<Stock> getAllIn(List<Stock.StockId> stockIds) {
        return stockRepository.findAllIn(stockIds);
    }
}
