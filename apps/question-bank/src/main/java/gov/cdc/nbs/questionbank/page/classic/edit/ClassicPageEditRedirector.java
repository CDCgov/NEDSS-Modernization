package gov.cdc.nbs.questionbank.page.classic.edit;

import gov.cdc.nbs.questionbank.page.classic.ClassicEditPagePreparer;
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
public class ClassicPageEditRedirector {

  private static final String EDIT = "/nbs/ManagePage.do";

  private final ClassicEditPagePreparer preparer;

  ClassicPageEditRedirector(final ClassicEditPagePreparer preparer) {
    this.preparer = preparer;
  }

  @GetMapping("/api/v1/pages/{page}/edit")
  ResponseEntity<Void> edit(@PathVariable final long page) {
    ReturningPageCookie pageCookie = new ReturningPageCookie(String.valueOf(page));
    preparer.prepare(page);

    String location =
        UriComponentsBuilder.fromPath(EDIT)
            .queryParam("fromWhere", "V")
            .queryParam("method", "editPageContentsLoad")
            .queryParam("waTemplateUid", page)
            .build()
            .toUriString();

    return ResponseEntity.status(HttpStatus.FOUND)
        .header(HttpHeaders.LOCATION, location)
        .headers(pageCookie.apply())
        .build();
  }
}
