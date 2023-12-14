package gov.cdc.nbs.questionbank.page.classic.redirect.incoming;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.questionbank.page.classic.ReturningPageCookie;
import springfox.documentation.annotations.ApiIgnore;


@ApiIgnore
@RestController
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class IncomingEditPageRedirector {
  private static final String EDIT_PAGE = "/page-builder/edit/page/";
  private static final String PAGE_LIBRARY = "/page-builder/pages";

  @SuppressWarnings("squid:S3752") // Allow GET and POST on same method
  @RequestMapping(path = "/api/v1/pages/return", method = {RequestMethod.GET, RequestMethod.POST})
  ResponseEntity<Void> returnToEdit(final HttpServletRequest request) {
    String location = ReturningPageCookie.resolve(request.getCookies())
        .map(c -> EDIT_PAGE + c.page())
        .orElse(PAGE_LIBRARY);

    return ResponseEntity.status(HttpStatus.FOUND)
        .header(HttpHeaders.LOCATION, location)
        .headers(ReturningPageCookie.empty().apply())
        .build();
  }
}
