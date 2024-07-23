package dz.pharmaconnect.pharmaconnectauth.model.api.responses.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleAuthResponse {


    @JsonProperty("sub")
    private String id;


    @NonNull
    private String email;


    @JsonProperty("family_name")
    private String familyName;

    @JsonProperty("given_name")
    private String givenName;


    private String locale;


    private String name;

    private String picture;


    @JsonProperty("email_verified")
    private boolean emailVerified = false;
}
