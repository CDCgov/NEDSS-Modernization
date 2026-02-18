package gov.cdc.nbs.authentication.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenStatus;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenValidation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NBSTokenValidatorTest {

  @Mock private JWTVerifier verifier;

  @InjectMocks private NBSTokenValidator validator;

  @Test
  void should_be_valid_auth_header() {
    // Given a request with a valid Authorization header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");

    // And a valid token
    DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
    when(verifier.verify("validtoken")).thenReturn(decodedJWT);
    when(decodedJWT.getSubject()).thenReturn("someUser");

    // When the request is validated
    TokenValidation validation = validator.validate(request);

    // Then the token will be valid
    assertEquals(TokenStatus.VALID, validation.status());
    assertEquals("someUser", validation.user());
  }

  @Test
  void should_be_valid_nbs_token() {
    // Given a request with a valid nbs_token cookie
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn(null);
    when(request.getCookies()).thenReturn(new Cookie[] {new Cookie("nbs_token", "validtoken")});

    // And a valid token
    DecodedJWT decodedJWT = Mockito.mock(DecodedJWT.class);
    when(verifier.verify("validtoken")).thenReturn(decodedJWT);
    when(decodedJWT.getSubject()).thenReturn("someUser");

    // When the request is validated
    TokenValidation validation = validator.validate(request);

    // Then the token will be valid
    assertEquals(TokenStatus.VALID, validation.status());
  }

  @Test
  void should_be_expired() {
    // Given a request with a valid Authorization header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("Bearer expiredToken");

    // And an expired token
    when(verifier.verify("expiredToken"))
        .thenThrow(new TokenExpiredException("expired", Instant.now()));

    // When the request is validated
    TokenValidation validation = validator.validate(request);

    // Then the token will be valid
    assertEquals(TokenStatus.EXPIRED, validation.status());
    assertNull(validation.user());
  }

  @Test
  void should_be_invalid() {
    // Given a request with a valid Authorization header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");

    // And an invalid token
    when(verifier.verify("invalidToken")).thenThrow(new JWTVerificationException("expired"));

    // When the request is validated
    TokenValidation validation = validator.validate(request);

    // Then the token will be valid
    assertEquals(TokenStatus.INVALID, validation.status());
    assertNull(validation.user());
  }

  @Test
  void should_be_unset() {
    // Given a request with a empty Authorization header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn(null);
    when(request.getCookies()).thenReturn(new Cookie[] {});

    // When the request is validated
    TokenValidation validation = validator.validate(request);

    // Then the token will be valid
    assertEquals(TokenStatus.UNSET, validation.status());
    assertNull(validation.user());
  }

  @Test
  void should_be_unset_invalid_auth() {
    // Given a request with an invalid Authorization header
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("Authorization")).thenReturn("bad");
    when(request.getCookies()).thenReturn(new Cookie[] {});

    // When the request is validated
    TokenValidation validation = validator.validate(request);

    // Then the token will be valid
    assertEquals(TokenStatus.UNSET, validation.status());
    assertNull(validation.user());
  }
}
