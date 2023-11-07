package gov.cdc.nbs.authentication;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import java.io.IOException;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import gov.cdc.nbs.authentication.session.SessionAuthenticator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenStatus;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenValidation;

@ExtendWith(MockitoExtension.class)
class NBSAuthenticationFilterTest {

  @Mock
  private NBSTokenValidator tokenValidator;

  @Mock
  private IgnoredPaths ignoredPaths;

  @Mock
  private NBSAuthenticationIssuer authIssuer;

  @Mock
  private SessionAuthenticator sessionAuthenticator;

  @InjectMocks
  private NBSAuthenticationFilter filter;

  @Test
  void should_set_auth_valid_token() throws ServletException, IOException {
    // Given a valid filter chain
    FilterChain chain = Mockito.mock(FilterChain.class);

    // And a request with a valid token
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(tokenValidator.validate(request)).thenReturn(new TokenValidation(TokenStatus.VALID, "user"));

    // when the filter is applied
    filter.doFilterInternal(request, null, chain);

    // then authorization is issued
    verify(authIssuer).issue("user", request, null);

    // and the filter chain is continued
    verify(chain).doFilter(request, null);
  }

  @Test
  void should_try_session_unset_token() throws ServletException, IOException {
    // Given a valid filter chain
    FilterChain chain = Mockito.mock(FilterChain.class);

    // And a request with no token
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(tokenValidator.validate(request)).thenReturn(new TokenValidation(TokenStatus.UNSET));

    // when the filter is applied
    filter.doFilterInternal(request, null, chain);

    // then the sessionAuthenticator is invoked
    verify(sessionAuthenticator).authenticate(request, null, chain);

    // then the auth is not applied by the filter
    verifyNoInteractions(authIssuer);

    // and the filter chain is not continued by the filter
    verifyNoInteractions(chain);
  }

  @Test
  void should_try_session_expired_token() throws ServletException, IOException {
    // Given a valid filter chain
    FilterChain chain = Mockito.mock(FilterChain.class);

    // And a request with an expired token
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(tokenValidator.validate(request)).thenReturn(new TokenValidation(TokenStatus.EXPIRED));

    // when the filter is applied
    filter.doFilterInternal(request, null, chain);

    // then the sessionAuthenticator is invoked
    verify(sessionAuthenticator).authenticate(request, null, chain);

    // then the auth is not applied by the filter
    verifyNoInteractions(authIssuer);

    // and the filter chain is not continued by the filter
    verifyNoInteractions(chain);
  }

  @Test
  void should_redirect_timeout() throws ServletException, IOException {
    // Given a valid filter chain
    FilterChain chain = Mockito.mock(FilterChain.class);

    // And a valid response
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    // and a request with an invalid
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(tokenValidator.validate(request)).thenReturn(new TokenValidation(TokenStatus.INVALID));

    // when the filter is applied
    filter.doFilterInternal(request, response, chain);

    // then a redirect is issued
    verify(response).setHeader(HttpHeaders.LOCATION, "/nbs/timeout");
    verify(response).setStatus(HttpStatus.FOUND.value());

    // then the sessionAuthenticator is not invoked
    verifyNoInteractions(sessionAuthenticator);

    // then the auth is not applied by the filter
    verifyNoInteractions(authIssuer);

    // and the filter chain is not continued by the filter
    verifyNoInteractions(chain);
  }

}
