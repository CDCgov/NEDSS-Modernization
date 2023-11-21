package gov.cdc.nbs.questionbank.page.print;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePageRequester;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
class PagePrintRedirector {

  private static final String LOCATION = "/nbs/PreviewPage.do";

  private final ClassicManagePageRequester managePageRequester;

  PagePrintRedirector(final ClassicManagePageRequester managePageRequester) {
    this.managePageRequester = managePageRequester;
  }

  ResponseEntity<Void> redirect(final long page) {

    prepare();

    URI location = UriComponentsBuilder.fromPath(LOCATION)
        .queryParam("method", "viewPageLoad")
        .queryParam("mode", "print")
        .queryParam("waTemplateUid", page)
        .build()
        .toUri();

    return redirect(location);
  }

  private void prepare() {
    this.managePageRequester.request();
  }

  ResponseEntity<Void> redirect(final URI location) {
    return ResponseEntity
        .status(HttpStatus.TEMPORARY_REDIRECT)
        .location(location)
        .build();
  }
}
