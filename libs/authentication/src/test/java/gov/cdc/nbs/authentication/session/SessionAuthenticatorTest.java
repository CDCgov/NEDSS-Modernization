package gov.cdc.nbs.authentication.session;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.authentication.NBSAuthenticationException;
import gov.cdc.nbs.authentication.NBSAuthenticationIssuer;
import gov.cdc.nbs.authentication.session.SessionAuthorization.Unauthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SessionAuthenticatorTest {

  @Mock private AuthorizedSessionResolver sessionResolver;

  @Mock private NBSAuthenticationIssuer authIssuer;

  @InjectMocks private SessionAuthenticator authenticator;

  @Test
  void should_authentication() {
    // Given a valid request and response
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    // And an authorized session
    when(sessionResolver.resolve(request)).thenReturn(new SessionAuthorization.Authorized("user"));

    // When the session is authenticated
    authenticator.authenticate(request, response);

    // Then nbs credentials should be issued
    verify(authIssuer).issue("user", response);
  }

  @Test
  void should_not_authentication() {
    // Given a valid request and response
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    // And an unauthorized session
    Unauthorized unauthorized = Mockito.mock(Unauthorized.class);

    when(sessionResolver.resolve(request)).thenReturn(unauthorized);

    // When the session is authenticated

    assertThatThrownBy(() -> authenticator.authenticate(request, response))
        .isInstanceOf(NBSAuthenticationException.class);

    // And nbs credentials should not be issued
    verifyNoInteractions(authIssuer);
  }
}
