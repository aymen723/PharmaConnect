package dz.pharmaconnect.pharmaconnectauth.model.api.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CredentialAuthRequest {
    @NotEmpty()
    private String email;
    @NotEmpty()
    private String password;
}
