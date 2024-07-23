package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import java.util.List;
import java.util.Optional;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.OrderSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.OrderService;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Order;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicOrderService implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Optional<Order> get(Long id) {
        return orderRepository.fetchOneById(id);
    }


    @Override
    public Optional<Order> fetch(Long id) {
        return this.get(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override

    public Order update(Order order) {
        var original = this.get(order.getId()).orElseThrow(() -> new FetchNotFoundException("order", order.getId()));

        if (order.getStatus() != null)
            original.setStatus(order.getStatus());

        if (order.getCheckoutPrice() != null)
            original.setCheckoutPrice(order.getCheckoutPrice());

        if (order.getDeliveryPrice() != null)
            original.setDeliveryPrice(order.getDeliveryPrice());


        return orderRepository.save(original);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> saveAll(Iterable<Order> collection) {
        return orderRepository.saveAll(collection);
    }

    @Override
    public List<Order> getByAccountId(Long accountId) {
        return orderRepository.findByAccountId(accountId);
    }

    @Override
    public Page<Order> getBySearchFilter(Pageable pageable, OrderSearchFilter filter) {
        return orderRepository.findBySearchFilter(pageable, filter);
    }
}
