package dz.pharmaconnect.pharmaconnectauth.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.AccountRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_username")
    private String username;

    @Column(name = "account_email", unique = true, nullable = false)
    private String email;


    @Column(name = "account_email_verified")
    private boolean emailVerified = false;

    @Column(name = "account_picture", columnDefinition = "TEXT")
    private String picture;

    @Column(name = "account_is_locked")
    private boolean locked = false;

    @Column(name = "account_is_active")
    private boolean active = true;

    @Column(name = "account_role")
    @Enumerated(EnumType.STRING)
    private AccountRole role;


    @Column(name = "account_security_code", nullable = false)
    private UUID securityCode;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    @JsonIgnore
    private Credential credential;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    @JsonIgnore
    private Location location;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
    @JsonIgnore
    OauthData oauthData;


    @JsonIgnore
    public boolean isAllowedToSignIn() {
        return this.active && !this.locked;
    }

}
