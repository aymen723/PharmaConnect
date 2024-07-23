package dz.pharmaconnect.pharmaconnectauth.controllers;

import dz.pharmaconnect.pharmaconnectauth.client.StockClient;
import dz.pharmaconnect.pharmaconnectauth.model.api.requests.LocationUpdateRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.requests.CreatePharmacyRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.requests.PasswordChangeRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.responses.Profile;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Account;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Credential;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Location;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.AccountRole;
import dz.pharmaconnect.pharmaconnectauth.repositories.database.CredentialRepository;
import dz.pharmaconnect.pharmaconnectauth.repositories.database.LocationRepository;
import dz.pharmaconnect.pharmaconnectauth.services.database.AccountService;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountsController {

    private final AccountService accountService;
    private final StockClient stockClient;
    private final PasswordEncoder encoder;
    private final CredentialRepository credentialRepository;
    private final LocationRepository locationRepository;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('SERVICE')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAll();

        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','SERVICE')")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
        Account account = accountService.get(accountId).orElseThrow(() -> new FetchNotFoundException("account", accountId));
        return ResponseEntity.ok(account);
    }

    @PostMapping("/pharmacy")
    @PreAuthorize("hasAnyAuthority('SERVICE')")
    public ResponseEntity<Account> createAccount(@RequestBody CreatePharmacyRequest request) {
        var credential = credentialRepository.save(Credential.builder().password(encoder.encode(request.getPassword())).build());
        var account = Account.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .emailVerified(true)
                .credential(credential)
                .picture(request.getPicture())
                .role(AccountRole.PHARMACY)
                .locked(false)
                .active(true)
                .securityCode(UUID.randomUUID())
                .build();

        var newAcc = accountService.save(account);
        credential.setAccount(account);
        credentialRepository.save(credential);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAcc);
    }

    @GetMapping("/{accountId}/profile")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Profile> getProfile(@PathVariable Long accountId, @RequestAttribute(required = false) Long userAccountId) {
        Account account = accountService.get(accountId).orElseThrow(() -> new FetchNotFoundException("account", accountId));
        if (account.getRole() == AccountRole.ADMIN) {
            Account userAccount = accountService.get(userAccountId).orElseThrow(() -> new FetchNotFoundException("account", userAccountId));
            if (userAccount.getRole() != AccountRole.ADMIN) throw new ForbiddenException();
        }
        var profile = Profile.from(account);
        if (account.getRole().equals(AccountRole.PHARMACY)) {
            profile.setPharmacy(stockClient.getPharmacyByAccountId(account.getId(), true));
        }
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("pwd")
    @PreAuthorize("hasAuthority('PHARMACY')")
    public ResponseEntity<Profile> patchPwd(@RequestAttribute Long userAccountId, @Valid @RequestBody PasswordChangeRequest request) {
        var account = accountService.get(userAccountId).orElseThrow(() -> new FetchNotFoundException("account", userAccountId));
        var credentials = account.getCredential();
        credentials.setPassword(encoder.encode(request.getNewPassword()));


        account.setCredential(credentialRepository.save(credentials));

        var newAcc = accountService.save(account);

        return ResponseEntity.ok(Profile.from(newAcc));

    }


    @PatchMapping("/location")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Profile> getProfile(@RequestAttribute Long userAccountId, @Valid @RequestBody LocationUpdateRequest request) {
        var account = accountService.get(userAccountId).orElseThrow(() -> new FetchNotFoundException("account", userAccountId));


        var location = account.getLocation();

        if (location == null) {
            location = Location.builder()
                    .account(account)

                    .build();
        }

        if (request.getPhoneNumber() != null) {
            location.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getAddress() != null) {
            location.setAddress(request.getAddress());
        }

        if (request.getX() != null && request.getY() != null) {
            location.setLongitude(request.getX());
            location.setLatitude(request.getY());
        }

        var loc = locationRepository.save(location);
        account.setLocation(loc);
        var newAcc = accountService.save(account);

        return ResponseEntity.ok(Profile.from(newAcc));


    }
}
