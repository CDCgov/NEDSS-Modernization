package gov.cdc.nbs.questionbank.page.information.change;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.RequestContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.Instant;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages/{page}")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class PageInformationChangeController {

  private final PageInformationChanger changer;

  PageInformationChangeController(final PageInformationChanger changer) {
    this.changer = changer;
  }

  @Operation(
      operationId = "change",
      summary = "Allows changing the Information of a page",
      description =
          "Allows changing message mapping guide, name, datamart, description, and related conditions of a Page.",
      tags = "Page Information")
  @PutMapping("/information")
  ResponseEntity<Void> change(
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details,
      @PathVariable long page,
      @RequestBody final PageInformationChangeRequest request) {

    RequestContext context = new RequestContext(details.getId(), Instant.now());

    changer.change(context, page, request);

    return ResponseEntity.accepted().build();
  }
}
