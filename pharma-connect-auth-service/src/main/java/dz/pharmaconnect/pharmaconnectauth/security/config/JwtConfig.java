package dz.pharmaconnect.pharmaconnectauth.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value(value = "${jwt.secret_key}")
    private String JWT_SECRET;

    @Bean
    public Algorithm getSignInAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }

    @Bean
    public JWTVerifier getJwtVerifier() {
        return JWT.require(getSignInAlgorithm())
                .withIssuer("PharmaConnect")
                .build();
    }
}
