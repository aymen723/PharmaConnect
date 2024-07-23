package dz.pharmaconnect.pharmaconnectauth.model.api.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public class RefreshTokenRequest {
    @NotNull
    private String refreshToken;


}
