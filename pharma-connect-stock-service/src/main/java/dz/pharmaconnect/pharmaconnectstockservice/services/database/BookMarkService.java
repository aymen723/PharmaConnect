package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.BookmarkSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookMarkService extends Dao<Bookmark, Long> {


    @Override
    Optional<Bookmark> get(Long id);

    @Override
    List<Bookmark> getAll();

    @Override
    Optional<Bookmark> fetch(Long id);

    @Override
    Bookmark save(Bookmark bookmark);

    @Override
    List<Bookmark> saveAll(Iterable<Bookmark> iterable);

    @Override
    Bookmark update(Bookmark bookmark);

    @Override
    void delete(Long id);

    Page<Bookmark> getBookMarksByFilter(Pageable pageable, BookmarkSearchFilter filter);


}
