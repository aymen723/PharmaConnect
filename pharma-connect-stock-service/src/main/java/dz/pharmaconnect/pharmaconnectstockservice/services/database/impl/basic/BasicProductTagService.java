package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import java.util.List;
import java.util.Optional;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.TagSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.ProductTagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.ProductTag;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.ProductTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicProductTagService implements ProductTagService {

    private final ProductTagRepository productTagRepository;

    @Override
    public Optional<ProductTag> get(Integer id) {
        return productTagRepository.findById(id);
    }


    @Override
    public Optional<ProductTag> fetch(Integer id) {
        return this.get(id);
    }

    @Override
    public List<ProductTag> getAll() {
        return productTagRepository.findAll();
    }

    @Override
    public ProductTag save(ProductTag productTag) {
        return productTagRepository.save(productTag);
    }

    @Override
    public ProductTag update(ProductTag productTag) {
        return productTagRepository.save(productTag);
    }

    @Override
    public void delete(Integer id) {
        productTagRepository.deleteById(id);
    }

    @Override
    public List<ProductTag> saveAll(Iterable<ProductTag> collection) {
        return productTagRepository.saveAll(collection);
    }

    @Override
    public Page<ProductTag> getAllFiltered(Pageable pageable, TagSearchFilter filter) {
        return productTagRepository.findProductTagsByFilter(pageable, filter);
    }
}
