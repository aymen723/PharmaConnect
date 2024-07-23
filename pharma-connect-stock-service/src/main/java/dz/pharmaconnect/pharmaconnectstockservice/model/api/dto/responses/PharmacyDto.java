package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.responses.PharmacyUpTime;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Location;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Pharmacy;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.UpTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class PharmacyDto {


    private Integer id;


    private String name;


    private LocationDto location;


    private boolean supportPayment = false;


    private boolean enabled = true;


    private boolean enabledSync = false;


    private String picture;


    private String phoneNumber;


    private Long accountId;

    private boolean para;


    private PharmacyUpTime upTimes;

    public static PharmacyDto map(Pharmacy pharmacy) {
        if (pharmacy == null) return null;
        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .name(pharmacy.getName())
                .location(LocationDto.map(pharmacy.getLocation()))
                .supportPayment(pharmacy.isSupportPayment())
                .enabled(pharmacy.isEnabled())
                .enabledSync(pharmacy.isEnabledSync())
                .picture(pharmacy.getPicture())
                .phoneNumber(pharmacy.getPhoneNumber())
                .accountId(pharmacy.getAccountId())
                .para(pharmacy.isPara())
                .upTimes(PharmacyUpTime.from(pharmacy.getUpTimes()))
                .build();
    }
}
