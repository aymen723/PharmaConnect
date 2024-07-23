package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import java.util.List;
import java.util.Optional;

import dz.pharmaconnect.pharmaconnectstockservice.services.database.Dao;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.TagTypeService;
import org.springframework.stereotype.Service;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.TagType;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.TagTypeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicTagTypeService implements TagTypeService {

    private final TagTypeRepository tagTypeRepository;

    @Override
    public Optional<TagType> get(Integer id) {
        return tagTypeRepository.findById(id);
    }


    @Override
    public Optional<TagType> fetch(Integer id) {
        return this.get(id);
    }

    @Override
    public List<TagType> getAll() {
        return tagTypeRepository.findAll();
    }

    @Override
    public TagType save(TagType tagType) {
        return tagTypeRepository.save(tagType);
    }

    @Override
    public TagType update(TagType tagType) {
        return tagTypeRepository.save(tagType);
    }

    @Override
    public void delete(Integer id) {
        tagTypeRepository.deleteById(id);
    }

    @Override
    public List<TagType> saveAll(Iterable<TagType> collection) {
        return tagTypeRepository.saveAll(collection);
    }
}
