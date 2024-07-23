package dz.pharmaconnect.pharmaconnectstockservice.services.database.impl.basic;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.BookmarkSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Bookmark;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.BookmarkRepository;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.BookMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BasicBookmarkService implements BookMarkService {
    private final BookmarkRepository bookmarkRepository;


    @Override
    public Optional<Bookmark> get(Long id) {
        return bookmarkRepository.findById(id);
    }

    @Override
    public List<Bookmark> getAll() {
        return bookmarkRepository.findAll();
    }

    @Override
    public Optional<Bookmark> fetch(Long id) {
        return bookmarkRepository.fetchById(id);
    }

    @Override
    public Bookmark save(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public List<Bookmark> saveAll(Iterable<Bookmark> iterable) {
        return null;
    }

    @Override
    public Bookmark update(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public void delete(Long id) {
        bookmarkRepository.deleteById(id);
    }

    @Override
    public Page<Bookmark> getBookMarksByFilter(Pageable pageable, BookmarkSearchFilter filter) {
        return bookmarkRepository.fetchByFilter(pageable, filter);
    }
}
