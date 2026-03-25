package gov.cdc.nbs.questionbank.page.clone;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//  "The intended client of this endpoint is a Classic redirection component so it will not be
// exposed through API documentation."
@Hidden
@RestController
@RequestMapping("/api/v1/pages/{page}/clone")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class PageCloneController {

  private final PageCloneRedirector redirector;

  PageCloneController(final PageCloneRedirector redirector) {
    this.redirector = redirector;
  }

  @GetMapping
  ResponseEntity<Void> print(@PathVariable final long page) {
    return this.redirector.redirect(page);
  }
}
