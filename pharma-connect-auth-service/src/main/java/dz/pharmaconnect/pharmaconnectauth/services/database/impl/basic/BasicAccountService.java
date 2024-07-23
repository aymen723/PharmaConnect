package dz.pharmaconnect.pharmaconnectauth.services.database.impl.basic;

import dz.pharmaconnect.pharmaconnectauth.model.api.requests.CreatePharmacyRequest;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Account;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.OauthData;
import dz.pharmaconnect.pharmaconnectauth.repositories.database.AccountRepository;
import dz.pharmaconnect.pharmaconnectauth.repositories.database.OauthDataRepository;
import dz.pharmaconnect.pharmaconnectauth.services.database.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasicAccountService implements AccountService {
    private final AccountRepository accountRepository;
    private final OauthDataRepository oauthDataRepository;


    @Override
    public Optional<Account> fetch(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> get(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account update(Account account) {
        return this.save(account);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Account> getByEmail(final String email) {
        return accountRepository.findOneByEmail(email);
    }

    @Override
    public Optional<OauthData> getOauthByCode(String code) {
        return oauthDataRepository.findOneByOauthCode(code);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public OauthData saveOauthData(OauthData oauthData) {
        return oauthDataRepository.save(oauthData);
    }

    @Override
    public Account createPharmacyAccount(CreatePharmacyRequest request) {
        return null;
    }
}
