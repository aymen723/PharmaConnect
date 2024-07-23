package dz.pharmaconnect.pharmaconnectstockservice.model.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.Optional;

@Data
public class LocationApproximation {
    private Integer range;
    private Point center;
    private boolean orderByDistance = false;

    public LocationApproximation(Integer range, Coordinate coordinate) {
        var factory = new GeometryFactory();
        this.range = range != null ? Math.max(range, 0) : null;
        this.center = coordinate == null ? null : factory.createPoint(coordinate);

    }

    public LocationApproximation(Integer range, Coordinate coordinate, boolean order) {
        var factory = new GeometryFactory();
        this.range = range != null ? Math.max(range, 0) : null;
        this.center = coordinate == null ? null : factory.createPoint(coordinate);
        this.orderByDistance = order;
    }

    public Integer useRange() {

        return this.range != null ? this.range : 0;
    }

    public Point useCenter() {
        return this.center != null ? this.center : new GeometryFactory().createPoint();
    }

    public boolean isValid() {
        return this.center != null & this.range != null;
    }
}
