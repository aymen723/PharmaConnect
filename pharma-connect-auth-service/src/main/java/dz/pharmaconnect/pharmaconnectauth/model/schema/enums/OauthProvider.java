package dz.pharmaconnect.pharmaconnectauth.model.schema.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthProvider {
    GOOGLE("https://www.googleapis.com/oauth2/v3/tokeninfo"), FACEBOOK("");
    private final String userAccessURL;


}
