package gov.cdc.nbs.questionbank.page.classic.create;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Hidden
@RestController
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ClassicCreatePageRedirector {
  private final ClassicCreatePagePreparer preparer;

  ClassicCreatePageRedirector(final ClassicCreatePagePreparer preparer) {
    this.preparer = preparer;
  }

  @GetMapping("/api/v1/pages/create")
  ResponseEntity<Void> view() {
    preparer.prepare();

    String location =
        UriComponentsBuilder.fromPath("/nbs/ManagePage.do")
            .queryParam("method", "addPageLoad")
            .build()
            .toUriString();

    return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, location).build();
  }
}
