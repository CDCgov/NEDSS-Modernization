package gov.cdc.nbs.authentication.token;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.TokenCreator;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Resolved the authenticated principal from the JWT token found on the request.
 */
@Component
public class JWTFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final UserService userService;
    private final JWTVerifier verifier;
    private final SecurityProperties securityProperties;
    private final TokenCreator creator;


    public JWTFilter(
        final UserService userService,
        final JWTVerifier verifier,
        final SecurityProperties securityProperties,
        final TokenCreator creator
    ) {
        this.userService = userService;
        this.verifier = verifier;
        this.securityProperties = securityProperties;
        this.creator = creator;
    }


    /**
     * On every request, validate the JWT, and load user details from the UserService.
     */
    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain
    )
        throws ServletException, IOException {
        resolveToken(request)
            .flatMap(this::verified)
            .map(DecodedJWT::getSubject)
            .map(userService::loadUserByUsername)
            .map(userDetails -> {
                response.addCookie(createJWTCookie(userDetails));
                return authenticate(userDetails, request);
            })
            .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveToken(final HttpServletRequest request) {
        try {
            return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .filter(Predicate.not(String::isBlank))
                .map(s -> s.substring(BEARER.length()));
        } catch (StringIndexOutOfBoundsException ex) {
            return Optional.empty();
        }
    }

    private Optional<DecodedJWT> verified(final String token) {
        try {
            DecodedJWT verified = verifier.verify(token);
            return Optional.of(verified);
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    private Cookie createJWTCookie(final NbsUserDetails userDetails) {
        NBSToken token = this.creator.forUser(userDetails.getUsername());
        Cookie cookie = token.asCookie();
        cookie.setMaxAge(securityProperties.getTokenExpirationSeconds());
        return cookie;
    }

    /**
     * Creates a {@link org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken}
     * that notifies Spring Security that the request has been authorized
     */
    private Authentication authenticate(
        final NbsUserDetails principal,
        final HttpServletRequest request
    ) {

        var authentication = new PreAuthenticatedAuthenticationToken(principal, null, principal.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

}
