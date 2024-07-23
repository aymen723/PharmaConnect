package dz.pharmaconnect.pharmaconnectauth.model.schema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credential")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id")

    private Long id;

    @Column(name = "credential_password", nullable = false)
    private String password;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @JsonIgnore
    private Account account;


}
