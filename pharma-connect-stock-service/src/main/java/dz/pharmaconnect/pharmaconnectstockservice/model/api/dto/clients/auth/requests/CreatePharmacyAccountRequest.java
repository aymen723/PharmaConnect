package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.clients.auth.requests;


import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Builder
public class CreatePharmacyAccountRequest {


    private String name;

    private String username;


    private String password;

    private String email;


    private String picture;
}
