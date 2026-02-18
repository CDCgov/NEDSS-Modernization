package gov.cdc.nbs.questionbank.page.publish;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.request.PagePublishRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PagePublishController {

  private final PagePublisher pagePublisher;

  public PagePublishController(final PagePublisher pagePublisher) {
    this.pagePublisher = pagePublisher;
  }

  @PutMapping("{id}/publish")
  public ResponseEntity<Void> publishPage(
      @PathVariable("id") Long pageId,
      @RequestBody PagePublishRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    pagePublisher.publishPage(pageId, request);
    return ResponseEntity.ok().build();
  }
}
