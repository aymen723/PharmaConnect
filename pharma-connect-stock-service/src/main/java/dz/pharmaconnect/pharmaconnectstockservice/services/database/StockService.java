package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.StockSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.SyncStockRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface StockService extends Dao<Stock, Stock.StockId>, PagedFetchable<Stock> {
    int syncStockState(SyncStockRequest request, Integer pharmacyId);

    int autocorrectUnidentifiedStock(UUID updateIdentifier, Integer pharmacyId);


    Page<Stock> getApproximateBySearch(Pageable pageable, StockSearchFilter filter);

    List<Stock> getAllIn(List<Stock.StockId> stockIds);
}
