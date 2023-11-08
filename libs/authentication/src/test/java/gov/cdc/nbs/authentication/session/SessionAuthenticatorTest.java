package gov.cdc.nbs.authentication.session;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.function.Consumer;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.authentication.NBSAuthenticationIssuer;
import gov.cdc.nbs.authentication.NBSUserCookie;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.session.SessionAuthorization.Unauthorized;

@ExtendWith(MockitoExtension.class)
class SessionAuthenticatorTest {

  @Mock
  private AuthorizedSessionResolver sessionResolver;

  @Mock
  private NBSAuthenticationIssuer authIssuer;

  @Mock
  private SecurityProperties securityProperties;

  @InjectMocks
  private SessionAuthenticator authenticator;

  @Test
  void should_authentication() throws IOException, ServletException {
    // Given a valid request and response
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    // And a filter chain
    FilterChain chain = Mockito.mock(FilterChain.class);

    // And an authorized session
    when(sessionResolver.resolve(request)).thenReturn(
        new SessionAuthorization.Authorized(
            null,
            new NBSUserCookie("user")));

    // When the session is authenticated
    authenticator.authenticate(request, response, chain);

    // Then nbs credentials should be issued
    verify(authIssuer).issue("user", request, response);

    // And the chain is continued
    verify(chain).doFilter(request, response);
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_not_authentication() throws IOException, ServletException {
    // Given a valid request and response
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    // And a filter chain
    FilterChain chain = Mockito.mock(FilterChain.class);

    // And an unauthorized session
    Unauthorized unauthorized = Mockito.mock(Unauthorized.class);
    Consumer<HttpServletResponse> consumer = Mockito.mock(Consumer.class);
    when(unauthorized.apply(securityProperties)).thenReturn(consumer);
    when(sessionResolver.resolve(request)).thenReturn(unauthorized);

    // When the session is authenticated
    authenticator.authenticate(request, response, chain);

    // Then the request is redirected
    verify(unauthorized).apply(securityProperties);
    verify(consumer).accept(response);

    // And nbs credentials should not be issued
    verifyNoInteractions(authIssuer);

    // And the chain is not continued
    verifyNoInteractions(chain);
  }
}
