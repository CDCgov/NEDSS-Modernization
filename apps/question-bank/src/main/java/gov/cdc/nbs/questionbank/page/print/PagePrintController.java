package gov.cdc.nbs.questionbank.page.print;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore(
    "The intended client of this endpoint is a Classic redirection component so it will not be exposed through API documentation.")
@RestController
@RequestMapping("/api/v1/pages/{page}/print")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class PagePrintController {

  private final PagePrintRedirector redirector;

  PagePrintController(final PagePrintRedirector redirector) {
    this.redirector = redirector;
  }

  @GetMapping
  ResponseEntity<Void> print(@PathVariable("page") final long page) {
    return this.redirector.redirect(page);
  }

}
