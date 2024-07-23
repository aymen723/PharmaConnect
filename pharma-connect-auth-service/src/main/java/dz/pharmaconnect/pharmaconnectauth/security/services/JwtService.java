package dz.pharmaconnect.pharmaconnectauth.security.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dz.pharmaconnect.pharmaconnectauth.model.schema.entities.Account;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final Algorithm jwtEncodingAlgorithm;
    private final JWTVerifier jwtVerifier;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expire_duration}")
    private Integer JWT_EXPIRE_DURATION;

    @Value("${jwt.refresh.expire_duration}")
    private Integer REFRESH_EXPIRE_DURATION;

    public Optional<DecodedJWT> verifyJwt(final String token) {
        try {
            return Optional.of(jwtVerifier.verify(token));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }

    }


    public Optional<String> extractToken(final HttpServletRequest request) {

        return getTokenFromHeaders(request);
    }


    private Optional<String> getTokenFromHeaders(final HttpServletRequest request) {
        var authorization = request.getHeader("authorization");
        System.out.println("authorization ::: " + authorization);
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            return Optional.empty();
        }
        final String token = authorization.substring(7);
        return Optional.of(token);
    }

    private Optional<String> getTokenFromCookies(final HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        var authCookieOpt = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("pharma-connect-auth")).findFirst();
        if (authCookieOpt.isEmpty()) {
            return Optional.empty();
        }
        var authCookie = authCookieOpt.get();
        return Optional.of(authCookie.getValue());
    }


    public JWTCreation createAccountJwt(Account account) {

        var expirationDate = Instant.now().plus(JWT_EXPIRE_DURATION, ChronoUnit.DAYS);
        var creationDate = Instant.now();
        var token = JWT.create()
                .withIssuer(issuer)
                .withSubject(account.getEmail())
                .withClaim("accountId", account.getId())
                .withClaim("role", account.getRole().name())
                .withClaim("tokenType", "ACCESS")
                .withIssuedAt(creationDate)
                .withExpiresAt(expirationDate)
                .sign(jwtEncodingAlgorithm);

        return JWTCreation.builder()
                .token(token)
                .expirationDate(expirationDate)
                .creationDate(creationDate)
                .build();

    }

    public JWTCreation createAccountRefreshToken(Account account) {
        var expirationDate = Instant.now().plus(REFRESH_EXPIRE_DURATION, ChronoUnit.DAYS);
        var creationDate = Instant.now();
        var token = JWT.create()
                .withIssuer(issuer)
                .withSubject(account.getEmail())
                .withClaim("accountId", account.getId())
                .withClaim("role", account.getRole().name())
                .withClaim("tokenType", "REFRESH")
                .withClaim("securityCode", account.getSecurityCode().toString())
                .withIssuedAt(creationDate)
                .withExpiresAt(expirationDate)
                .sign(jwtEncodingAlgorithm);

        return JWTCreation.builder()
                .token(token)
                .expirationDate(expirationDate)
                .creationDate(creationDate)
                .build();
    }

    public JWTCreation createServiceJwt(String serviceName) {

        var creationDate = Instant.now();
        var token = JWT.create()
                .withIssuer(issuer)
                .withSubject(serviceName)
                .withClaim("role", "SERVICE")
                .withClaim("tokenType", "ACCESS")
                .withIssuedAt(creationDate)
//                .withExpiresAt(expirationDate)
                .sign(jwtEncodingAlgorithm);

        return JWTCreation.builder()
                .token(token)
//                .expirationDate(expirationDate)
                .creationDate(creationDate)
                .build();
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JWTCreation {
        private String token;
        private Instant expirationDate;

        private Instant creationDate;

    }
}
