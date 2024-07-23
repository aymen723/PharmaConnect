package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import java.util.*;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.PharmacySearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.StockRepository;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.PharmacyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.PharmacyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicPharmacyService implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final StockRepository stockRepository;

    @Override
    public Optional<Pharmacy> get(Integer id) {
        return pharmacyRepository.findById(id);
    }


    @Override
    public Optional<Pharmacy> fetch(Integer id) {
        return this.get(id);
    }

    @Override
    public List<Pharmacy> getAll() {
        return pharmacyRepository.findAll();
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) {
        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public Pharmacy update(Pharmacy pharmacy) {
        return pharmacyRepository.save(pharmacy);
    }

    @Override
    public void delete(Integer id) {
        pharmacyRepository.deleteById(id);
    }


    @Override
    public List<Pharmacy> saveAll(Iterable<Pharmacy> collection) {
        return pharmacyRepository.saveAll(collection);
    }

    @Override
    public Page<Pharmacy> getAllFiltered(Pageable pageable, PharmacySearchFilter filter) {
//        if (filter.getStockFilter() != null && filter.getStockFilter().canFilterPharmacies()) {
//            var pharmacyIds = stockRepository.findStockPharmacyIdsBySearch(filter.getStockFilter());
//            filter = PharmacySearchFilter.builder()
//                    .ids(new HashSet<>(pharmacyIds))
//                    .approx(filter.getApprox())
//                    .build();
//
//
//        }


        return pharmacyRepository.findAllFiltered(pageable, filter);
    }

    @Override
    public Optional<Pharmacy> getByAccountId(Long accountId) {
        return pharmacyRepository.findByAccountId(accountId);
    }
}
