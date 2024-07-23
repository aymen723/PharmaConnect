package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.clients.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {


    private Long id;


    private String username;


    private String email;


    private boolean emailVerified;


    private String picture;


    private boolean locked;

    private AccountRole role;


    private boolean active;

    public enum AccountRole {
        CLIENT, PHARMACY
    }


}
