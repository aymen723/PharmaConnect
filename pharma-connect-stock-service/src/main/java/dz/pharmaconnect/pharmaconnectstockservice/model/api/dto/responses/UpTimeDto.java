package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.UpTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
public class UpTimeDto {


    private Long id;


    private DayOfWeek day;


    private LocalTime openTime;


    private LocalTime closeTime;

    private boolean working = true;


    public static UpTimeDto map(UpTime upTime) {
        if (upTime == null) return null;
        return UpTimeDto.builder()
                .id(upTime.getId())
                .day(upTime.getDay())
                .openTime(upTime.getOpenTime())
                .closeTime(upTime.getCloseTime())
                .working(upTime.isWorking())
                .build();
    }
}
