package dz.pharmaconnect.delivery.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import dz.pharmaconnect.delivery.client.AuthClient;
import dz.pharmaconnect.delivery.security.ApplicationServiceDetails;
import dz.pharmaconnect.delivery.security.ApplicationUserDetails;
import dz.pharmaconnect.delivery.security.services.JWTService;
import jakarta.persistence.Column;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTSecurityFilter extends OncePerRequestFilter {
    private final AuthClient authClient;
    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        System.out.println("getting in");
        final var tokenStr = jwtService.extractAccessToken(request);
        if (tokenStr.isEmpty()) {

            filterChain.doFilter(request, response);
            return;
        }
        final var tokenOpt = jwtService.verifyToken(tokenStr.get());

        if (tokenOpt.isEmpty()) {


            filterChain.doFilter(request, response);
            return;
        }


        var token = tokenOpt.get();
        var role = token.getClaim("role").asString();
        System.out.println("role :: " + role);
        if ("SERVICE".equals(role)) {

            authorizeService(request, token);
        } else {
            authorizeUser(request, token);
        }


        filterChain.doFilter(request, response);
    }


    public void authorizeService(HttpServletRequest request, DecodedJWT token) {
        var serviceDetails = new ApplicationServiceDetails();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(serviceDetails, null, serviceDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    public void authorizeUser(HttpServletRequest request, DecodedJWT token) {
        Long accountId = token.getClaim("accountId").asLong();
        if ((SecurityContextHolder.getContext().getAuthentication() == null)) {

            var userDetails = new ApplicationUserDetails(authClient.getAccountById(accountId));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            request.setAttribute("userAccountId", accountId);
        }
    }
}
