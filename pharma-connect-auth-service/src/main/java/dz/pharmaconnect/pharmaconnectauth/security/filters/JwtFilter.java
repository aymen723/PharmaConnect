package dz.pharmaconnect.pharmaconnectauth.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import dz.pharmaconnect.pharmaconnectauth.model.security.ApplicationServiceDetails;
import dz.pharmaconnect.pharmaconnectauth.model.security.ApplicationUserDetails;
import dz.pharmaconnect.pharmaconnectauth.security.services.JwtService;
import dz.pharmaconnect.pharmaconnectauth.services.database.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final AccountService accountService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final var tokenStr = jwtService.extractToken(request);
        if (tokenStr.isEmpty()) {
            System.out.println(":::: token Empty");
            filterChain.doFilter(request, response);
            return;
        }
        final var tokenOpt = jwtService.verifyJwt(tokenStr.get());

        if (tokenOpt.isEmpty()) {

            System.out.println("::::: token invalid");
            filterChain.doFilter(request, response);
            return;
        }


        var token = tokenOpt.get();
        var tokenType = token.getClaim("tokenType").asString();
        var tokenClaims = token.getClaims();
        System.out.println("claims : " + tokenClaims);
        if (!tokenType.equals("ACCESS")) {
            filterChain.doFilter(request, response);
            return;
        }
        var role = token.getClaim("role").asString();

        System.out.println(":::::: token role is :" + role);
        if ("SERVICE".equals(role)) {
            var serviceDetails = new ApplicationServiceDetails();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(serviceDetails, null, serviceDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
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

            var userDetails = new ApplicationUserDetails(accountService.get(accountId).orElseThrow(() -> new IllegalStateException("no account found")));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            request.setAttribute("userAccountId", accountId);
        }
    }
}
