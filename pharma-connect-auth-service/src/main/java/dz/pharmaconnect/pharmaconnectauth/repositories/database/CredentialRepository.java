package dz.pharmaconnect.pharmaconnectauth.repositories.database;

import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
}
