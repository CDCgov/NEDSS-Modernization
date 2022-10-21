package gov.cdc.nbs.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import gov.cdc.nbs.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final static String AUTHORIZATION = "Authorization";
    private final static String BEARER = "Bearer ";
    private final UserService userService;
    private final JWTVerifier verifier;

    /**
     * On every request, validate the JWT, and load user details from the
     * UserService.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        verifyAndDecodeJWT(request)
                .map(userService::findUserByToken)
                .map(userDetails -> buildPreAuthenticatedToken(userDetails, request))
                .ifPresent(preAuthToken -> SecurityContextHolder.getContext().setAuthentication(preAuthToken));
        filterChain.doFilter(request, response);
    }

    /**
     * Pulls the JWT from the "Authorization" header and validates it against the
     * {@link gov.cdc.nbs.config.security.JWTSecurityConfig#jwtVerifier verifier}
     */
    private Optional<DecodedJWT> verifyAndDecodeJWT(HttpServletRequest request) {
        try {
            return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.substring(BEARER.length()))
                    .map(s -> verifier.verify(s));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    /**
     * Creates a
     * {@link org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken}
     * that notifies Spring Security that the request has been authorized
     */
    private PreAuthenticatedAuthenticationToken buildPreAuthenticatedToken(NbsUserDetails principal,
            HttpServletRequest request) {
        var pat = new PreAuthenticatedAuthenticationToken(principal, null, principal.getAuthorities());
        pat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return pat;
    }

}
