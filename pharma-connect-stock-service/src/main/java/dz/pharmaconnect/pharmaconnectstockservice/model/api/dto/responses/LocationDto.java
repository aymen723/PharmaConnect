package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Location;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.locationtech.jts.geom.Coordinate;

import org.locationtech.jts.geom.Point;

@Builder
@Data
public class LocationDto {


    private Integer id;


    private Coordinate coordinates;


    private String googleUrl;


    public static LocationDto map(Location location) {
        if (location == null) return null;

        return LocationDto.builder()
                .id(location.getId())
                .coordinates(location.getCoordinates())
                .googleUrl(location.getGoogleUrl())
                .build();

    }
}
