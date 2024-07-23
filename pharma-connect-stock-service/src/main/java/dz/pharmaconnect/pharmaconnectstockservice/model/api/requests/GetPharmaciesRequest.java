package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;

@Data
public class GetPharmaciesRequest {


    private Coordinate coordinates;
}
