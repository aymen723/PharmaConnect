package dz.pharmaconnect.pharmaconnectauth.repositories.database;

import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findOneByEmail(String email);
}
