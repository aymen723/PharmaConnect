package dz.pharmaconnect.pharmaconnectauth.model.api.requests;

import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.OauthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthenticationRequest {

    private OauthProvider provider;


    private String accessToken;

    private String idToken;
}
