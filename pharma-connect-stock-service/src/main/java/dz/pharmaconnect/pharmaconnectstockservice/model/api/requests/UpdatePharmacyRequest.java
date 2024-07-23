package dz.pharmaconnect.pharmaconnectstockservice.model.api.requests;

import dz.pharmaconnect.pharmaconnectstockservice.validation.PhoneNumber;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePharmacyRequest {


    private String newName;


    @PhoneNumber
    private String newPhoneNumber;
}
