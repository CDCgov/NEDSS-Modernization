package gov.cdc.nbs.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;

class NBSSessionAuthenticationEntryPointTest {

  private NBSSessionAuthenticationEntryPoint authenticationEntryPoint;

  @BeforeEach
  void setUp() {
    authenticationEntryPoint = new NBSSessionAuthenticationEntryPoint();
  }

  @Test
  void commence_insufficient_not_json() throws IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    AuthenticationException ex = new InsufficientAuthenticationException("uh oh");

    authenticationEntryPoint.commence(request, response, ex);

    assertEquals(302, response.getStatus());
    assertEquals("/nbs/timeout", response.getHeader(HttpHeaders.LOCATION));
  }

  @Test
  void commence_insufficient_with_json() throws IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    MockHttpServletResponse response = new MockHttpServletResponse();
    AuthenticationException ex = new InsufficientAuthenticationException("uh oh");

    authenticationEntryPoint.commence(request, response, ex);

    assertEquals(403, response.getStatus());
  }

  @Test
  void commence_other_error() throws IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    AuthenticationException ex = new InternalAuthenticationServiceException("uh oh");

    authenticationEntryPoint.commence(request, response, ex);

    assertEquals(403, response.getStatus());
  }
}
