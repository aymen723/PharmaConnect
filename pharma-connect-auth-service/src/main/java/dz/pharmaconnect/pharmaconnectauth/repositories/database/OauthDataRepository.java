package dz.pharmaconnect.pharmaconnectauth.repositories.database;

import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.OauthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OauthDataRepository extends JpaRepository<OauthData, Integer> {

    public Optional<OauthData> findOneByOauthCode(String oauthCode);


}
