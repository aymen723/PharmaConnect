package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pharmacy_location", indexes = {@Index(columnList = "location_coordinates")})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Integer id;

    @Column(name = "location_coordinates", columnDefinition = "GEOGRAPHY(Point, 4326)")
    @JsonIgnore
    private Point point;

    @Column(name = "location_google_url")
    private String googleUrl;


    public Coordinate getCoordinates() {
        return this.getPoint().getCoordinate();
    }


}
