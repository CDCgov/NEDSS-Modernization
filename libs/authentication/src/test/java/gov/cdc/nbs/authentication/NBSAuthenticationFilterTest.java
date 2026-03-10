package gov.cdc.nbs.authentication;

import static org.mockito.Mockito.*;

import gov.cdc.nbs.authentication.session.SessionAuthenticator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenStatus;
import gov.cdc.nbs.authentication.token.NBSTokenValidator.TokenValidation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NBSAuthenticationFilterTest {

  @Mock private NBSTokenValidator tokenValidator;

  @Mock private NBSAuthenticationIssuer authIssuer;

  @Mock private SessionAuthenticator sessionAuthenticator;

  @InjectMocks private NBSAuthenticationFilter filter;

  @Test
  void should_set_auth_valid_token() throws ServletException, IOException {
    // Given a valid filter chain
    FilterChain chain = Mockito.mock(FilterChain.class);

    // And a request with a valid token
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(tokenValidator.validate(request))
        .thenReturn(new TokenValidation(TokenStatus.VALID, "user"));

    // when the filter is applied
    filter.doFilterInternal(request, null, chain);

    // then authorization is issued
    verify(authIssuer).issue("user", null);
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
    verify(sessionAuthenticator).authenticate(request, null);

    // then the auth is not applied by the filter
    verifyNoInteractions(authIssuer);
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
    verify(sessionAuthenticator).authenticate(request, null);

    // then the auth is not applied by the filter
    verifyNoInteractions(authIssuer);
  }
}
