package gov.cdc.nbs.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.context.SecurityContextHolder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import gov.cdc.nbs.authentication.config.JWTSecurityConfig;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.util.AuthObjectUtil;

class JWTFilterTest {
    @Mock
    private UserService userService;

    @Spy
    private SecurityProperties properties = new SecurityProperties(
            "46z+oDurrJnxfaAz6h4Eg1saP7NSeHuQQ/Yq4vh+mbi4B4QYThc/M+22VtPO469Z",
            "test-issuer",
            60_000);

    @Spy
    private Algorithm algorithm;

    @Spy
    private JWTVerifier verifier;

    @InjectMocks
    private JWTFilter filter;

    @BeforeEach
    void setup() {
        algorithm = buildAlgorithm();
        verifier = buildVerifier();
        SecurityContextHolder.getContext().setAuthentication(null);
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void should_validate() throws ServletException, IOException {
        NbsUserDetails actual = AuthObjectUtil.userDetails();
        // mocks
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        String token = "Bearer " + createToken(false);
        when(request.getHeader("Authorization")).thenReturn(token);

        when(userService.findUserByToken(Mockito.any())).thenReturn(actual);

        // method to test
        filter.doFilterInternal(request, response, filterChain);

        // validate
        NbsUserDetails user = (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertNotNull(user);
        assertEquals(actual.getFirstName(), user.getFirstName());
        assertEquals(actual.getLastName(), user.getLastName());
        assertEquals(actual.getId(), user.getId());
        assertEquals(actual.isEnabled(), user.isEnabled());
        assertEquals(actual.isMasterSecurityAdmin(), user.isMasterSecurityAdmin());
        assertEquals(actual.isProgramAreaAdmin(), user.isProgramAreaAdmin());
        assertEquals(actual.getToken(), user.getToken());
        assertEquals(actual.getUsername(), user.getUsername());

        assertEquals(1, user.getAuthorities().size());
        NbsAuthority actualAuthority = actual.getAuthorities().iterator().next();
        NbsAuthority userAuthority = user.getAuthorities().iterator().next();
        assertEquals(actualAuthority.getAuthority(), userAuthority.getAuthority());
        assertEquals(actualAuthority.getBusinessObject(), userAuthority.getBusinessObject());
        assertEquals(actualAuthority.getBusinessOperation(), userAuthority.getBusinessOperation());
        assertEquals(actualAuthority.getJurisdiction(), userAuthority.getJurisdiction());
        assertEquals(actualAuthority.getProgramArea(), userAuthority.getProgramArea());
        assertEquals(actualAuthority.getProgramAreaUid(), userAuthority.getProgramAreaUid());

        verify(response).addCookie(Mockito.any());
    }

    @Test
    void should_not_validate_bad_token() throws ServletException, IOException {
        // mocks
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        String token = "Bearer this-is-a-bad-token";
        when(request.getHeader("Authorization")).thenReturn(token);

        when(userService.findUserByToken(Mockito.any(DecodedJWT.class))).thenReturn(AuthObjectUtil.userDetails());

        // method to test
        filter.doFilterInternal(request, response, filterChain);

        // validate
        verify(userService, never()).findUserByToken(Mockito.any());
        verify(response, never()).addCookie(Mockito.any());

        assertThrows(NullPointerException.class,
                () -> SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void should_not_validate_blank_token() throws ServletException, IOException {
        // mocks
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        String token = "";
        when(request.getHeader("Authorization")).thenReturn(token);

        when(userService.findUserByToken(Mockito.any(DecodedJWT.class))).thenReturn(AuthObjectUtil.userDetails());

        // method to test
        filter.doFilterInternal(request, response, filterChain);

        // validate
        verify(userService, never()).findUserByToken(Mockito.any());
        verify(response, never()).addCookie(Mockito.any());

        assertThrows(NullPointerException.class,
                () -> SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void should_not_validate_expired() throws ServletException, IOException {
        // mocks
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        String token = "Bearer " + createToken(true);
        when(request.getHeader("Authorization")).thenReturn(token);

        when(userService.findUserByToken(Mockito.any(DecodedJWT.class))).thenReturn(AuthObjectUtil.userDetails());

        // method to test
        filter.doFilterInternal(request, response, filterChain);

        // validate
        verify(userService, never()).findUserByToken(Mockito.any());
        verify(response, never()).addCookie(Mockito.any());

        assertThrows(NullPointerException.class,
                () -> SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }


    private String createToken(boolean isExpired) {
        Instant now = Instant.now();
        Instant expiry;
        if (isExpired) {
            expiry = Instant.now().minus(Duration.ofMillis(properties.getTokenExpirationMillis() + 1000L));
        } else {
            expiry = Instant.now().plus(Duration.ofMillis(properties.getTokenExpirationMillis()));
        }
        return JWT
                .create()
                .withIssuer(properties.getTokenIssuer())
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withSubject(AuthObjectUtil.userDetails().getUsername())
                .sign(algorithm);
    }

    private Algorithm buildAlgorithm() {
        JWTSecurityConfig config = new JWTSecurityConfig();
        return config.jwtAlgorithm(properties);
    }

    private JWTVerifier buildVerifier() {
        JWTSecurityConfig config = new JWTSecurityConfig();
        return config.jwtVerifier(algorithm, properties);
    }
}
