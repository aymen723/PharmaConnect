package dz.pharmaconnect.pharmaconnectstockservice.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.UpTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpTimeInserterGroup {
    private Integer id;
    private List<UpTimeInserter> values;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpTimeInserter {
        private LocalTime openTime;
        private LocalTime closeTime;
        private DayOfWeek day;


        public UpTime getUpTime() {


            return UpTime.builder()
                    .openTime(LocalTime.from(this.openTime))
                    .closeTime(LocalTime.from(this.closeTime))
                    .day(this.day)
                    .build();
        }
    }
}
