package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.search.redirect.SearchRedirect;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Hidden
@RestController
class SimpleSearchRedirectionController {

  private final SimpleSearchResolver resolver;
  private final SearchRedirect searchRedirect;

  SimpleSearchRedirectionController(
      final SimpleSearchResolver resolver,
      final SearchRedirect searchRedirect
  ) {
    this.resolver = resolver;
    this.searchRedirect = searchRedirect;
  }

  /**
   * Intercepts legacy home page search requests, pulls out the current user from the JSESSIONID, the search criteria
   * from the incomingParams map, and forwards the request to the modernization search page
   */
  @PostMapping("/nbs/redirect/simpleSearch")
  ResponseEntity<Void> redirect(@RequestParam final Map<String, String> incoming) {
    URI location = this.resolver.resolve(incoming)
        .map(this::toSearch)
        .orElseGet(this::fallback);

    return ResponseEntity.status(HttpStatus.FOUND)
        .location(location)
        .build();
  }

  private URI toSearch(final SimpleSearch search) {
    return UriComponentsBuilder.fromPath(searchRedirect.base())
        .path("/simple/{type}/{criteria}")
        .build()
        .expand(search.type(), search.criteria())
        .toUri();

  }

  private URI fallback() {
    return UriComponentsBuilder.fromPath(searchRedirect.base())
        .build()
        .toUri();
  }

}
