package dz.pharmaconnect.pharmaconnectauth.services.database;

import dz.pharmaconnect.pharmaconnectauth.model.api.requests.CreatePharmacyRequest;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Account;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.OauthData;

import java.util.Optional;

public interface AccountService extends DTOService<Account, Long> {
    Optional<Account> getByEmail(final String email);

    Optional<OauthData> getOauthByCode(final String code);

    Account save(Account account);

    OauthData saveOauthData(OauthData oauthData);

    Account createPharmacyAccount(CreatePharmacyRequest request);

}
