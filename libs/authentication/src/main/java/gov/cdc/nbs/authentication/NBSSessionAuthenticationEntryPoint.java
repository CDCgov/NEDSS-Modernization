package gov.cdc.nbs.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

class NBSSessionAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final AuthenticationException authException)
      throws IOException {

    // Redirect to timeout on permissions error unless the request was asking for JSON and
    // can be strongly intuited is a true API call and the redirect does more harm than good
    // and we should instead return the 403
    if (authException instanceof InsufficientAuthenticationException
        && !MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader(HttpHeaders.ACCEPT))) {
      response.setStatus(HttpStatus.FOUND.value());
      response.setHeader(HttpHeaders.LOCATION, "/nbs/timeout");
    } else {
      response.sendError(403, "Access Denied");
    }
  }
}
