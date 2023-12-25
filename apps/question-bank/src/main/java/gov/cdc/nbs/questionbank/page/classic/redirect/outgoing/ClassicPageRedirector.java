package gov.cdc.nbs.questionbank.page.classic.redirect.outgoing;

import gov.cdc.nbs.questionbank.page.classic.ReturningPageCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ClassicPageRedirector {

  public ResponseEntity<Void> redirect(final long page, final URI location) {
    return ResponseEntity
        .status(HttpStatus.TEMPORARY_REDIRECT)
        .location(location)
        .headers(new ReturningPageCookie(page).apply())
        .build();
  }
}
