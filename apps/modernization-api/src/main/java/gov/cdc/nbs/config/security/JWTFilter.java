package gov.cdc.nbs.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String TOKEN_COOKIE_NAME = "nbs_token";
    private final UserService userService;
    private final JWTVerifier verifier;
    private final SecurityProperties securityProperties;

    /**
     * On every request, validate the JWT, and load user details from the UserService.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        verifyAndDecodeJWT(request)
                .map(userService::findUserByToken)
                .map(userDetails -> {
                    response.addCookie(createJWTCookie(userDetails));
                    return buildPreAuthenticatedToken(userDetails, request);
                })
                .ifPresent(preAuthToken -> SecurityContextHolder.getContext().setAuthentication(preAuthToken));
        filterChain.doFilter(request, response);
    }

    public Cookie createJWTCookie(NbsUserDetails userDetails) {
        var cookie = new Cookie(TOKEN_COOKIE_NAME, userDetails.getToken());
        cookie.setMaxAge(securityProperties.getTokenExpirationSeconds());
        cookie.setPath("/");
        return cookie;
    }

    /**
     * Pulls the JWT from the "Authorization" header and validates it against the
     * {@link gov.cdc.nbs.config.security.JWTSecurityConfig#jwtVerifier verifier}
     */
    public Optional<DecodedJWT> verifyAndDecodeJWT(HttpServletRequest request) {
        try {
            return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                    .map(s -> !s.isBlank() ? s.substring(BEARER.length()) : s)
                    .map(verifier::verify);
        } catch (JWTVerificationException | StringIndexOutOfBoundsException ex) {
            return Optional.empty();
        }
    }

    /**
     * Creates a {@link org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken}
     * that notifies Spring Security that the request has been authorized
     */
    public PreAuthenticatedAuthenticationToken buildPreAuthenticatedToken(NbsUserDetails principal,
            HttpServletRequest request) {
        var pat = new PreAuthenticatedAuthenticationToken(principal, null, principal.getAuthorities());
        pat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return pat;
    }

}
