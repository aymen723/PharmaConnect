package dz.pharmaconnect.pharmaconnectstockservice.utils;

import org.locationtech.jts.geom.Coordinate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public class SearchParamsValidator {

    public static Pageable paginate(Integer page, Integer pageSize) {
        return paginate(page, pageSize, 100);

    }

    public static Pageable paginate(Integer page, Integer pageSize, int maxPageSize) {
        page = page == null ? 0 : page;
        page = page - 1;
        page = Math.max(page, 0);
        pageSize = pageSize == null ? 10 : pageSize;
        pageSize = Math.max(Math.min(pageSize, maxPageSize), 0);

        return PageRequest.of(page, pageSize);
    }

    public static <T extends Collection<?>> T noEmpty(T collection) {
        if (collection != null && collection.isEmpty()) {
            return null;
        }
        return collection;
    }

    public static Coordinate makeCoordinate(Double x, Double y) {
        Coordinate coordinates = null;
        if (x != null & y != null) {
            coordinates = new Coordinate(x, y);
        }
        return coordinates;
    }
}
