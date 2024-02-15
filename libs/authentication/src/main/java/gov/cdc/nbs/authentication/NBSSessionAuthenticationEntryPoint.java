package gov.cdc.nbs.authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class NBSSessionAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final AuthenticationException authException
  )
      throws IOException {

    if (authException instanceof InsufficientAuthenticationException) {
      response.setStatus(HttpStatus.FOUND.value());
      response.setHeader(HttpHeaders.LOCATION, "/nbs/timeout");
    } else {
      response.sendError(403, "Access Denied");
    }
  }
}
