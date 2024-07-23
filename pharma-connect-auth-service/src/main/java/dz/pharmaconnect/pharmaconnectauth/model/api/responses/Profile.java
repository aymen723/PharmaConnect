package dz.pharmaconnect.pharmaconnectauth.model.api.responses;

import dz.pharmaconnect.pharmaconnectauth.model.api.client.Pharmacy;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Account;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.AccountRole;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder

public class Profile {
    private Long id;
    private String username;
    private String email;
    private boolean emailVerified = false;
    private String picture;
    private AccountRole role;
    private Pharmacy pharmacy;


    public static Profile from(Account account) {
        return Profile.builder()
                .id(account.getId())
                .username(account.getUsername())
                .email(account.getEmail())
                .emailVerified(account.isEmailVerified())
                .picture(account.getPicture())
                .role(account.getRole())
                .build();
    }
}
