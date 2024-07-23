package dz.pharmaconnect.pharmaconnectauth.services.auth;

import dz.pharmaconnect.pharmaconnectauth.model.api.requests.CredentialAuthRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.requests.OAuthenticationRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.responses.SignInResponse;
import dz.pharmaconnect.pharmaconnectauth.security.services.JwtService;

public interface LoginService {
    SignInResponse refreshAccount(String refreshToken);

    SignInResponse oauth2SignIn(OAuthenticationRequest signInRequest);

    JwtService.JWTCreation getServiceToken(String serviceName);

    SignInResponse credentialLogin(CredentialAuthRequest request);


}
