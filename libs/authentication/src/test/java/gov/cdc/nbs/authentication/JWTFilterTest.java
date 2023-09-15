package gov.cdc.nbs.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import gov.cdc.nbs.authentication.config.JWTSecurityConfig;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.util.AuthObjectUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTFilterTest {
    @Mock
    private UserService userService;

    private Algorithm algorithm;

    private JWTFilter filter;

    @BeforeEach
    void setup() {

        SecurityProperties properties = new SecurityProperties(
            "46z+oDurrJnxfaAz6h4Eg1saP7NSeHuQQ/Yq4vh+mbi4B4QYThc/M+22VtPO469Z",
            "test-issuer",
            60_000
        );

        JWTSecurityConfig config = new JWTSecurityConfig();

        algorithm = config.jwtAlgorithm(properties);
        JWTVerifier verifier = config.jwtVerifier(algorithm, properties);

        TokenCreator creator = new TokenCreator(
            Clock.systemDefaultZone(),
            algorithm,
            properties
        );
        SecurityContextHolder.getContext().setAuthentication(null);

        filter = new JWTFilter(userService, verifier, properties, creator);
    }


    @Test
    void should_validate() throws ServletException, IOException {
        NbsUserDetails details = mock(NbsUserDetails.class);
        // mocks
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        String token = "Bearer " + createToken(Instant.now().plus(5, ChronoUnit.HOURS));
        when(request.getHeader("Authorization")).thenReturn(token);

        when(userService.loadUserByUsername(any())).thenReturn(details);

        // method to test
        filter.doFilterInternal(request, response, filterChain);

        // validate
        NbsUserDetails principal =
            (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assertThat(principal).isSameAs(details);

        verify(response).addCookie(any());
    }

    @Test
    void should_not_validate_bad_token() throws ServletException, IOException {
        // mocks
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        String token = "Bearer this-is-a-bad-token";
        when(request.getHeader("Authorization")).thenReturn(token);

        // method to test
        filter.doFilterInternal(request, response, filterChain);

        // validate
        verify(userService, never()).loadUserByUsername(any());
        verify(response, never()).addCookie(any());

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void should_not_validate_blank_token() throws ServletException, IOException {
        // mocks
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("");

        // method to test
        filter.doFilterInternal(request, response, filterChain);

        // validate
        verify(userService, never()).loadUserByUsername(any());
        verify(response, never()).addCookie(any());

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void should_not_validate_expired() throws ServletException, IOException {
        // mocks
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        String token = "Bearer " + createToken(Instant.now().minus(5, ChronoUnit.HOURS));
        when(request.getHeader("Authorization")).thenReturn(token);

        // method to test
        filter.doFilterInternal(request, response, filterChain);

        // validate
        verify(userService, never()).loadUserByUsername(any());
        verify(response, never()).addCookie(any());

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }


    private String createToken(final Instant expiringAt) {
        return JWT
            .create()
            .withIssuer("test-issuer")
            .withIssuedAt(Instant.now())
            .withExpiresAt(expiringAt)
            .withSubject(AuthObjectUtil.userDetails().getUsername())
            .sign(algorithm);
    }

}
