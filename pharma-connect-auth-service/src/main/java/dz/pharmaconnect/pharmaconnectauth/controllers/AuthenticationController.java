package dz.pharmaconnect.pharmaconnectauth.controllers;


import dz.pharmaconnect.pharmaconnectauth.client.StockClient;
import dz.pharmaconnect.pharmaconnectauth.model.api.requests.CredentialAuthRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.requests.OAuthenticationRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.responses.SignInResponse;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.AccountRole;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.OauthProvider;
import dz.pharmaconnect.pharmaconnectauth.security.services.JwtService;
import dz.pharmaconnect.pharmaconnectauth.services.auth.LoginService;
import dz.pharmaconnect.pharmaconnectauth.services.database.AccountService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AccountService accountService;
    private final StockClient stockClient;

    private final LoginService loginService;


    @PostMapping("/google")
    @PreAuthorize("permitAll()")
    public ResponseEntity<SignInResponse> googleLogin(@RequestBody OAuthenticationRequest signInRequest, HttpServletResponse response) {
        signInRequest.setProvider(OauthProvider.GOOGLE);
        var respData = loginService.oauth2SignIn(signInRequest);

        return ResponseEntity.ok(respData);
    }


    @GetMapping("/service/{serviceName}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> getServerToken(@PathVariable String serviceName) {
        return ResponseEntity.ok(loginService.getServiceToken(serviceName).getToken());
    }


    @GetMapping("/refresh")
    public ResponseEntity<SignInResponse> refreshToken(@RequestParam String refreshToken) {


        var res = loginService.refreshAccount(refreshToken);

        if (res.getProfile().getRole().equals(AccountRole.PHARMACY)) {
            res.getProfile().setPharmacy(stockClient.getPharmacyByAccountId(res.getId(), true));
        }
        return ResponseEntity.ok(res);

    }


    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<SignInResponse> loginWithCredentials(@RequestBody @Valid CredentialAuthRequest request) {

        var res = loginService.credentialLogin(request);

        if (res.getProfile().getRole().equals(AccountRole.PHARMACY)) {
            res.getProfile().setPharmacy(stockClient.getPharmacyByAccountId(res.getId(), true));
        }

        return ResponseEntity.ok(res);
    }


}
