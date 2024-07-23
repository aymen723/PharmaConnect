package dz.pharmaconnect.pharmaconnectauth.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.OauthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth_data")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OauthData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id")
    private Integer oauthId;

    @Column(name = "oauth_code")
    private String oauthCode;

    @Column(name = "oauth_provider")
    @Enumerated(EnumType.STRING)
    private OauthProvider provider;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;
}
