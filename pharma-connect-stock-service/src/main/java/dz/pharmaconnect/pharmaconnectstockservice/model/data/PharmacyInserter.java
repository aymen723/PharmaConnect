package dz.pharmaconnect.pharmaconnectstockservice.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Location;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyInserter {
    private String id;

    private String name;

    private List<Double> location;

    private String picture;

    private Integer upTimeId;

    public Pharmacy getPharmacy() {
        final var geoFac = new GeometryFactory();
        return Pharmacy.builder()
                .name(this.name)
                .picture(this.picture)
                .enabled(true)
                .supportPayment(true)
                .para(false)

                .location(
                        Location.builder()
                                .point(geoFac.createPoint(new Coordinate(this.location.get(1), this.location.get(0))))
                                .build()
                )
                .build();
    }


}
