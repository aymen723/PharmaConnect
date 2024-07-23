package dz.pharmaconnect.pharmaconnectauth.model.api.responses;

import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Account;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.AccountRole;
import dz.pharmaconnect.pharmaconnectauth.security.services.JwtService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private JwtService.JWTCreation tokenData;
    private JwtService.JWTCreation refreshTokenData;
    private Long id;
    private AccountRole role;
    private Profile profile;


}
