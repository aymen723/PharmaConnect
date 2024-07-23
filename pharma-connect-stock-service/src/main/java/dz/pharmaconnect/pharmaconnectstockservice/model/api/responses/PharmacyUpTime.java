package dz.pharmaconnect.pharmaconnectstockservice.model.api.responses;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.UpTimeDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.UpTime;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyUpTime {

    private Set<UpTimeDto> uptimes;

    public boolean isOpen() {
        if (uptimes == null) return false;
        var today = LocalDateTime.now();

        var todayUpTime = this.uptimes.stream().filter(uptime -> uptime.getDay().equals(today.getDayOfWeek())).findFirst();

        if (todayUpTime.isEmpty()) return false;

        var startTime = todayUpTime.get().getOpenTime();
        var endTime = todayUpTime.get().getCloseTime();
        var nowTime = today.toLocalTime();
        return startTime.isBefore(nowTime) && endTime.isAfter(nowTime);


    }

    public static PharmacyUpTime from(Set<UpTime> uptimes) {
        return new PharmacyUpTime(uptimes.stream().map(UpTimeDto::map).collect(Collectors.toSet()));
    }


}
