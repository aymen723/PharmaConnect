package dz.pharmaconnect.pharmaconnectauth.services.auth.impl.basic;

import dz.pharmaconnect.pharmaconnectauth.model.api.requests.CredentialAuthRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.requests.OAuthenticationRequest;
import dz.pharmaconnect.pharmaconnectauth.model.api.responses.Profile;
import dz.pharmaconnect.pharmaconnectauth.model.api.responses.SignInResponse;
import dz.pharmaconnect.pharmaconnectauth.model.api.responses.external.GoogleAuthResponse;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Account;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.OauthData;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.AccountRole;
import dz.pharmaconnect.pharmaconnectauth.model.schema.enums.OauthProvider;
import dz.pharmaconnect.pharmaconnectauth.security.services.JwtService;
import dz.pharmaconnect.pharmaconnectauth.services.auth.LoginService;
import dz.pharmaconnect.pharmaconnectauth.services.auth.OauthProviderService;
import dz.pharmaconnect.pharmaconnectauth.services.database.AccountService;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicLoginService implements LoginService {


    private final AccountService accountService;
    private final JwtService jwtService;
    private final OauthProviderService oauthProviderService;
    private final PasswordEncoder encoder;


    @Override
    public SignInResponse credentialLogin(CredentialAuthRequest request) {

        var account = accountService.getByEmail(request.getEmail()).orElseThrow(() -> new FetchNotFoundException("account", request.getEmail()));
        var credential = account.getCredential();

        if (credential == null || !encoder.matches(request.getPassword(), credential.getPassword())) {
            throw new ForbiddenException();
        }

        if (!account.isAllowedToSignIn()) throw new ForbiddenException();

        var accessToken = jwtService.createAccountJwt(account);
        var refreshToken = jwtService.createAccountRefreshToken(account);

        return new SignInResponse(accessToken, refreshToken, account.getId(), account.getRole(), Profile.from(account));


    }

    @Override
    public SignInResponse oauth2SignIn(OAuthenticationRequest signInRequest) {


        Account userAccount = switch (signInRequest.getProvider()) {
            case GOOGLE -> {
                yield this.googleOauth2(signInRequest);
            }
            case FACEBOOK -> {
                throw new UnsupportedOperationException();
            }

        };


        var token = jwtService.createAccountJwt(userAccount);
        var refreshToken = jwtService.createAccountRefreshToken(userAccount);
        return new SignInResponse(token, refreshToken, userAccount.getId(), userAccount.getRole(), Profile.from(userAccount));

    }

    private Account googleOauth2(OAuthenticationRequest signInRequest) {

        final GoogleAuthResponse googleInfo;
        if (signInRequest.getAccessToken() != null) {
            googleInfo = oauthProviderService.getAccessTokenAuthInfo(signInRequest.getAccessToken());
        } else {
            googleInfo = oauthProviderService.getIdTokenAuthInfo(signInRequest.getIdToken());
        }

        System.out.println("login data :" + googleInfo);

        try {

            Account userAccount;

            var oauthData = accountService.getOauthByCode(googleInfo.getId());

            if (oauthData.isEmpty()) {
                userAccount = Account.builder()
                        .username(googleInfo.getName())
                        .role(AccountRole.CLIENT)
                        .email(googleInfo.getEmail())
                        .emailVerified(googleInfo.isEmailVerified())
                        .active(true)
                        .locked(false)
                        .picture(googleInfo.getPicture())
                        .securityCode(UUID.randomUUID())
                        .build();
                var newOauthData = OauthData.builder()
                        .oauthCode(googleInfo.getId())
                        .provider(signInRequest.getProvider())
                        .account(userAccount)
                        .build();
                newOauthData = accountService.saveOauthData(newOauthData);
                userAccount.setOauthData(newOauthData);

//                userAccount = accountService.save(newAccount);
//                newOauthData.setAccount(userAccount);
//                newOauthData = accountService.saveOauthData(newOauthData);
//                userAccount.setOauthData(newOauthData);
//                userAccount = accountService.save(newAccount);


            } else {
                var oauthConfirmedData = oauthData.get();

                if (oauthConfirmedData.getProvider() != OauthProvider.GOOGLE) {
                    throw new NoSuchElementException("oauth data with provider not found");
                }

                userAccount = oauthConfirmedData.getAccount();

                if (!userAccount.isAllowedToSignIn()) throw new ForbiddenException();


                userAccount.setPicture(googleInfo.getPicture());
                userAccount.setEmailVerified(googleInfo.isEmailVerified());


            }

            return accountService.save(userAccount);
        } catch (RestClientException e) {


            throw new IllegalStateException("invalid google access token");

        }
    }

    @Override
    public JwtService.JWTCreation getServiceToken(String serviceName) {
        return jwtService.createServiceJwt(serviceName);
    }


    @Override
    public SignInResponse refreshAccount(String refreshToken) {
        var decodedRefreshToken = jwtService.verifyJwt(refreshToken).orElseThrow(() -> new IllegalStateException("invalid refresh token"));
        String tokenType = decodedRefreshToken.getClaim("tokenType").asString();
        if (!tokenType.equals("REFRESH")) throw new IllegalStateException("not a refresh token");
        Long accountId = decodedRefreshToken.getClaim("accountId").asLong();
        var account = accountService.get(accountId).orElseThrow(() -> new FetchNotFoundException("account", accountId));
        String securityCode = decodedRefreshToken.getClaim("securityCode").asString();
//        if (!securityCode.equals(account.getSecurityCode().toString())) throw new IllegalStateException();
        if (!account.isAllowedToSignIn()) throw new ForbiddenException();
        var accessToken = jwtService.createAccountJwt(account);
        var newRefreshToken = jwtService.createAccountRefreshToken(account);
        return new SignInResponse(accessToken, newRefreshToken, account.getId(), account.getRole(), Profile.from(account));
    }


}
