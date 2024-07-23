package dz.pharmaconnect.pharmaconnectstockservice.security.filter;

import dz.pharmaconnect.pharmaconnectstockservice.clients.AuthClient;
import dz.pharmaconnect.pharmaconnectstockservice.security.ApplicationServiceDetails;
import dz.pharmaconnect.pharmaconnectstockservice.security.ApplicationUserDetails;
import dz.pharmaconnect.pharmaconnectstockservice.security.jwt.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var extractedToken = jwtService.extractToken(request);

        if (!extractedToken.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = extractedToken.get();

        var verifiedToken = jwtService.verifyJwt(token);

        if (!verifiedToken.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }

        var decodedToken = verifiedToken.get();

        String role = decodedToken.getClaim("role").asString();
        if ("SERVICE".equals(role)) {
            System.out.println("role is :::: " + role);
            var serviceDetails = new ApplicationServiceDetails(List.of(new SimpleGrantedAuthority(role)));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(serviceDetails, null, serviceDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            if ((SecurityContextHolder.getContext().getAuthentication() == null)) {
                var accountId = decodedToken.getClaim("accountId").asLong();
                var userDetails = new ApplicationUserDetails(authClient.getAccount(accountId));
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                request.setAttribute("userAccountId", accountId);
            }


        }

        filterChain.doFilter(request, response);
    }
}
