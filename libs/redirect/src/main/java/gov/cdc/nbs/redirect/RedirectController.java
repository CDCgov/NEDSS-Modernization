package gov.cdc.nbs.redirect;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class RedirectController {

  private final DefaultRedirectionPath defaultRedirection;

  RedirectController(final DefaultRedirectionPath defaultRedirection) {
    this.defaultRedirection = defaultRedirection;
  }

  @GetMapping("/redirect")
  public ResponseEntity<Void> redirect(HttpServletRequest request) {
    String location = request.getHeader(HttpHeaders.LOCATION);
    if (location == null) {
      location = defaultRedirection.path();
    }
    return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, location).build();
  }
}
