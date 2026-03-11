package gov.cdc.nbs.questionbank.page.classic.redirect.incoming;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.classic.ReturningPageCookie;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class IncomingEditPageRedirector {
  private static final String EDIT_PAGE = "/page-builder/pages/";
  private static final String PAGE_LIBRARY = "/page-builder/pages";

  private final RecentlyCreatedPageFinder finder;

  public IncomingEditPageRedirector(final RecentlyCreatedPageFinder finder) {
    this.finder = finder;
  }

  @SuppressWarnings("squid:S3752") // Allow GET and POST on same method
  @RequestMapping(
      path = "/api/v1/pages/return",
      method = {RequestMethod.GET, RequestMethod.POST})
  ResponseEntity<Void> returnToEdit(
      final HttpServletRequest request, @AuthenticationPrincipal final NbsUserDetails user) {
    String location = getLocation(request, user.getId());

    return ResponseEntity.status(HttpStatus.FOUND)
        .header(HttpHeaders.LOCATION, location)
        .headers(ReturningPageCookie.empty().apply())
        .build();
  }

  /**
   * If the returning page cookie is present, route to the provided page. Else see if a page was
   * recently created by the current user and route to that page. Else fall back to the page library
   */
  private String getLocation(final HttpServletRequest request, long user) {
    String pageId =
        ReturningPageCookie.resolve(request.getCookies())
            .map(ReturningPageCookie::page)
            .orElseGet(() -> finder.findRecentlyCreatedBy(user).map(String::valueOf).orElse(null));

    if (pageId != null) {
      return EDIT_PAGE + pageId + "/edit";
    }
    return PAGE_LIBRARY;
  }
}
