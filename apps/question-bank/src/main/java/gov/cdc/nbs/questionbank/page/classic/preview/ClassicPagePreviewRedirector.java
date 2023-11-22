package gov.cdc.nbs.questionbank.page.classic.preview;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import gov.cdc.nbs.questionbank.page.classic.ClassicPreviewPagePreparer;
import gov.cdc.nbs.questionbank.page.classic.ReturningPageCookie;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ClassicPagePreviewRedirector {

  private static final String PREVIEW = "/nbs/PreviewPage.do";

  private final ClassicPreviewPagePreparer preparer;

  ClassicPagePreviewRedirector(final ClassicPreviewPagePreparer preparer) {
    this.preparer = preparer;
  }

  @GetMapping("/api/v1/pages/{page}/preview")
  ResponseEntity<Void> view(@PathVariable("page") final long page) {
    ReturningPageCookie pageCookie = new ReturningPageCookie(String.valueOf(page));
    preparer.prepare();

    URI location = UriComponentsBuilder.fromPath(PREVIEW)
        .queryParam("from", "L")
        .queryParam("method", "viewPageLoad")
        .queryParam("waTemplateUid", page)
        .build()
        .toUri();

    return ResponseEntity.status(HttpStatus.FOUND)
        .header(HttpHeaders.LOCATION, location.toString())
        .headers(pageCookie.apply())
        .build();
  }

}
