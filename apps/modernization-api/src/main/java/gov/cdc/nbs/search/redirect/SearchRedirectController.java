package gov.cdc.nbs.search.redirect;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
class SearchRedirectController {
  private final SearchRedirect searchRedirect;



  SearchRedirectController(final SearchRedirect searchRedirect) {
    this.searchRedirect = searchRedirect;
  }



  /**
   * Intercepts legacy advanced search page requests, pulls out the current user from the JSESSIONID, and forwards the
   * request to the modernization search page
   */
  @Hidden
  @GetMapping("/nbs/redirect/advancedSearch")
  RedirectView redirectAdvancedSearch() {
    return new RedirectView(searchRedirect.base());
  }
}
