package dz.pharmaconnect.pharmaconnectauth.services.auth;

import dz.pharmaconnect.pharmaconnectauth.model.api.responses.external.GoogleAuthResponse;

public interface OauthProviderService {

    GoogleAuthResponse getAccessTokenAuthInfo(String accessToken);

    GoogleAuthResponse getIdTokenAuthInfo(String idToken);
}
