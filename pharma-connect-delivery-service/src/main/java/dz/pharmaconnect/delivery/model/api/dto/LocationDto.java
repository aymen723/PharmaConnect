package dz.pharmaconnect.delivery.model.api.dto;

import dz.pharmaconnect.delivery.model.schema.entities.Location;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LocationDto {




    private Long id;

    private Double latitude;

    private Double longitude;

    private String address;

    public  static LocationDto map(Location location) {
        if(location == null) return null;

        return LocationDto.builder()
                .id(location.getId())
                .address(location.getAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}
