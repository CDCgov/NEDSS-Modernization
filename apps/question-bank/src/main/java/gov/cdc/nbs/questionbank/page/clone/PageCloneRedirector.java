package gov.cdc.nbs.questionbank.page.clone;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicPageRedirector;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class PageCloneRedirector {

  private static final String LOCATION = "/nbs/ManagePage.do";

  private final ClassicClonePagePreparer preparer;
  private final ClassicPageRedirector redirector;

  PageCloneRedirector(
      final ClassicClonePagePreparer preparer, final ClassicPageRedirector redirector) {
    this.preparer = preparer;
    this.redirector = redirector;
  }

  ResponseEntity<Void> redirect(final long page) {
    this.preparer.prepare(page);

    URI location =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("method", "clonePageLoad")
            .build()
            .toUri();

    return this.redirector.redirect(page, location);
  }
}
