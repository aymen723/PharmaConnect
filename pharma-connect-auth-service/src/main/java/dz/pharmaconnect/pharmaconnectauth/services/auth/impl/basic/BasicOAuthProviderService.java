package dz.pharmaconnect.pharmaconnectauth.services.auth.impl.basic;

import dz.pharmaconnect.pharmaconnectauth.model.api.responses.external.GoogleAuthResponse;

import dz.pharmaconnect.pharmaconnectauth.services.auth.OauthProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class BasicOAuthProviderService implements OauthProviderService {

    private final String GOOGLE_ACCESS_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    private final String GOOGLE_ID_INFO_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo";
 

    @Override
    public GoogleAuthResponse getAccessTokenAuthInfo(String accessToken) {
        final RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + accessToken);
        URI uri = UriComponentsBuilder
                .fromHttpUrl(this.GOOGLE_ACCESS_INFO_URL)
                .build()
                .toUri();


        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<GoogleAuthResponse> googleResponse = template.exchange(uri, HttpMethod.GET, entity, GoogleAuthResponse.class);
        return googleResponse.getBody();
    }

    @Override
    public GoogleAuthResponse getIdTokenAuthInfo(String idToken) {
        final RestTemplate template = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromHttpUrl(this.GOOGLE_ID_INFO_URL)
                .queryParam("id_token", idToken)
                .build()
                .toUri();

        ResponseEntity<GoogleAuthResponse> googleResponse = template.exchange(uri, HttpMethod.GET, null, GoogleAuthResponse.class);

        return googleResponse.getBody();
    }
}
