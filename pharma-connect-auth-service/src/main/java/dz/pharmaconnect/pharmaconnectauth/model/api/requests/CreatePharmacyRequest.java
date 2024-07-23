package dz.pharmaconnect.pharmaconnectauth.model.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class CreatePharmacyRequest {

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;


    private String picture;
}
