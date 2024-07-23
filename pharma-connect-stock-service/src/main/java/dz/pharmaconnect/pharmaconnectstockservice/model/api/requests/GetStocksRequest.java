package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;

@Getter
@Setter

public class GetStocksRequest {
    private Coordinate coordinates;
}
