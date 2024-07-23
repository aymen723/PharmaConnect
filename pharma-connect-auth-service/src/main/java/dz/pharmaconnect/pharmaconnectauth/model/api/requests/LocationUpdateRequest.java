package dz.pharmaconnect.pharmaconnectauth.model.api.requests;


import dz.pharmaconnect.pharmaconnectauth.validation.PhoneNumber;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationUpdateRequest {

    @PhoneNumber
    private String phoneNumber;


    private String address;

    @NotNull
    private Double x;
    @NotNull
    private Double y;
}
