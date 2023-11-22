package gov.cdc.nbs.questionbank.page.classic.preview;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import gov.cdc.nbs.questionbank.page.classic.ClassicPreviewPagePreparer;
import gov.cdc.nbs.questionbank.page.classic.ReturningPageCookie;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ClassicPagePreviewRedirector {

  private static final String LOCATION = "/nbs/PreviewPage.do";
  private static final String EDIT_PAGE_LOCATION = "/page-builder/edit/page/";
  private static final String PAGE_LIBRARY = "/page-builder/manage/pages";

  private final ClassicPreviewPagePreparer preparer;

  ClassicPagePreviewRedirector(final ClassicPreviewPagePreparer preparer) {
    this.preparer = preparer;
  }

  @GetMapping("/api/v1/pages/{page}/preview")
  ResponseEntity<Void> view(@PathVariable("page") final long page) {
    ReturningPageCookie pageCookie = new ReturningPageCookie(String.valueOf(page));
    preparer.prepare();

    URI location = UriComponentsBuilder.fromPath(LOCATION)
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

  @RequestMapping(path = "/api/v1/pages/return", method = {RequestMethod.GET, RequestMethod.POST})
  ResponseEntity<Void> returnToEdit(final HttpServletRequest request) {
    String location = ReturningPageCookie.resolve(request.getCookies())
        .map(c -> EDIT_PAGE_LOCATION + c.page())
        .orElse(PAGE_LIBRARY);

    return ResponseEntity.status(HttpStatus.FOUND)
        .header(HttpHeaders.LOCATION, location)
        .headers(ReturningPageCookie.empty().apply())
        .build();
  }

}
