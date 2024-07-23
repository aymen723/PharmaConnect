package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Location;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.LocationRepository;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BasicLocationService implements LocationService {

    private final LocationRepository locationRepository;


    @Override
    public Optional<Location> get(Integer id) {
        return locationRepository.findById(id);

    }

    @Override
    public Optional<Location> fetch(Integer id) {
        return this.get(id);
    }

    @Override
    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location update(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Location> saveAll(Iterable<Location> collection) {
        return locationRepository.saveAll(collection);
    }
}
