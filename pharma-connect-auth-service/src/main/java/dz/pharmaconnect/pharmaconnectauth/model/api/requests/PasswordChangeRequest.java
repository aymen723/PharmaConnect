package dz.pharmaconnect.pharmaconnectauth.model.api.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordChangeRequest {

    @NotNull
    @NotEmpty
    @Min(8)
    private String newPassword;

}
