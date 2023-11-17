package gov.cdc.nbs.questionbank.page.classic.preview;

import java.net.URI;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import gov.cdc.nbs.questionbank.page.classic.ClassicPreviewPagePreparer;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ClassicPagePreviewRedirector {

  private static final String LOCATION = "/nbs/PreviewPage.do";

  private final ClassicPreviewPagePreparer preparer;

  ClassicPagePreviewRedirector(final ClassicPreviewPagePreparer preparer) {
    this.preparer = preparer;
  }

  @GetMapping("/pages/{page}/preview")
  RedirectView view(
      @PathVariable("page") final long page) {

    preparer.prepare();

    URI location = UriComponentsBuilder.fromPath(LOCATION)
        .queryParam("from", "L")
        .queryParam("method", "viewPageLoad")
        .queryParam("waTemplateUid", page)
        .build()
        .toUri();

    return new RedirectView(location.toString());
  }


}
