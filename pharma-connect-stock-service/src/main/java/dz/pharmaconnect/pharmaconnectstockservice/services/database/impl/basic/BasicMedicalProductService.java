package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import java.util.List;
import java.util.Optional;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.ProductSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.MedicalProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.MedicalProduct;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.MedicalProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicMedicalProductService implements MedicalProductService {

    private final MedicalProductRepository medicalProductRepository;

    @Override
    public Optional<MedicalProduct> get(Integer id) {
        return medicalProductRepository.findById(id);
    }


    @Override
    public Optional<MedicalProduct> fetch(Integer id) {
        return medicalProductRepository.findOneByIdJoin(id);
    }

    @Override
    public List<MedicalProduct> getAll() {
        return medicalProductRepository.findAll();
    }

    @Override
    public MedicalProduct save(MedicalProduct medicalProduct) {
        return medicalProductRepository.save(medicalProduct);
    }

    @Override
    public MedicalProduct update(MedicalProduct medicalProduct) {
        return medicalProductRepository.save(medicalProduct);
    }

    @Override
    public void delete(Integer id) {
        medicalProductRepository.deleteById(id);
    }

    @Override
    public List<MedicalProduct> saveAll(Iterable<MedicalProduct> collection) {
        return medicalProductRepository.saveAll(collection);
    }

    @Override
    public Page<MedicalProduct> getByFilter(Pageable pageable, ProductSearchFilter filter) {

        return medicalProductRepository.findMedicalProductsByFilter(pageable, filter);
    }
}
