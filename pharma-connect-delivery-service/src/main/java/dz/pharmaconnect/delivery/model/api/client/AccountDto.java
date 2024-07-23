package dz.pharmaconnect.delivery.model.api.client;

import lombok.Data;

@Data
public class AccountDto {

    private Long id;


    private String username;


    private String email;


    private String password;


    private boolean emailVerified;


    private String picture;


    private boolean locked;

    private AccountRole role;


    private boolean active;

    public enum AccountRole {
         CLIENT, PHARMACY
    }
}
