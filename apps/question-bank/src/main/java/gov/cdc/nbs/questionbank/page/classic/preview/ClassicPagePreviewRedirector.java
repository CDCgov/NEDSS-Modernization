package gov.cdc.nbs.questionbank.page.classic.preview;

import gov.cdc.nbs.questionbank.page.classic.ClassicPreviewPagePreparer;
import gov.cdc.nbs.questionbank.page.classic.ReturningPageCookie;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Hidden
@RestController
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ClassicPagePreviewRedirector {

  private static final String PREVIEW = "/nbs/PreviewPage.do";

  private final ClassicPreviewPagePreparer preparer;

  ClassicPagePreviewRedirector(final ClassicPreviewPagePreparer preparer) {
    this.preparer = preparer;
  }

  @GetMapping("/api/v1/pages/{page}/preview")
  ResponseEntity<Void> view(@PathVariable final long page) {
    ReturningPageCookie pageCookie = new ReturningPageCookie(String.valueOf(page));
    preparer.prepare();

    String location =
        UriComponentsBuilder.fromPath(PREVIEW)
            .queryParam("from", "L")
            .queryParam("method", "viewPageLoad")
            .queryParam("waTemplateUid", page)
            .build()
            .toUriString();

    return ResponseEntity.status(HttpStatus.FOUND)
        .header(HttpHeaders.LOCATION, location)
        .headers(pageCookie.apply())
        .build();
  }
}
