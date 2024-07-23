package dz.pharmaconnect.pharmaconnectstockservice.security.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JWTVerifier jwtVerifier;

    public Optional<DecodedJWT> verifyJwt(String token) {
        try {
            var decodedJwt = jwtVerifier.verify(token);
            return Optional.of(decodedJwt);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<String> extractToken(HttpServletRequest request) {
        var authorization = request.getHeader("authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            var token = authorization.substring(7);
            return Optional.of(token);
        }
        return Optional.empty();
    }
}
