package gov.cdc.nbs.questionbank.page.template;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.RequestContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/pages/{page}/template")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class PageTemplateController {

  private final PageTemplateCreator creator;

  PageTemplateController(final PageTemplateCreator creator) {
    this.creator = creator;
  }

  @Operation(
      operationId = "createTemplate",
      summary = "Creates a Template from the Page that can be used as a starting point for new Pages.",
      tags = "Pages"
  )
  @ApiOperation(value = "", nickname = "createTemplate")
  @PostMapping
  ResponseEntity<Void> create(
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details,
      @PathVariable("page") final long page,
      @RequestBody final CreateTemplateRequest request
  ) {
    RequestContext context = new RequestContext(details.getId(), Instant.now());

    this.creator.create(context, page, request);

    return ResponseEntity.accepted()
        .build();
  }

}
