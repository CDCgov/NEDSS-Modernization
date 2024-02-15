package gov.cdc.nbs.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class NBSAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final AccessDeniedException accessDeniedException
  ) {
    response.setStatus(HttpStatus.FORBIDDEN.value());
  }
}
