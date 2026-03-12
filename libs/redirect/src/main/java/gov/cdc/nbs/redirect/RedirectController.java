package gov.cdc.nbs.redirect;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class RedirectController {

  private final DefaultRedirectionPath defaultRedirection;

  RedirectController(final DefaultRedirectionPath defaultRedirection) {
    this.defaultRedirection = defaultRedirection;
  }

  @SuppressWarnings(
      "squid:S3752") // Allow GET and POST on same method as requests forwarded from classic for
  // redirection can be either
  @RequestMapping(
      path = "/redirect",
      method = {RequestMethod.POST, RequestMethod.GET})
  public ResponseEntity<Void> redirect(HttpServletRequest request) {
    String location = request.getHeader(HttpHeaders.LOCATION);
    if (location == null) {
      location = defaultRedirection.path();
    }
    return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, location).build();
  }
}
