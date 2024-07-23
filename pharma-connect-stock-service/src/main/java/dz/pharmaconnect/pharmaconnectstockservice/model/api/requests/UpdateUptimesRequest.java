package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class UpdateUptimesRequest {
    @NotNull
    private DayOfWeek day;
    @NotNull
    private LocalTime openTime;
    @NotNull
    private LocalTime closeTime;
}
