package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.OrderSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService extends Dao<Order, Long> {

    List<Order> getByAccountId(Long accountId);

    Page<Order> getBySearchFilter(Pageable pageable, OrderSearchFilter filter);
}
