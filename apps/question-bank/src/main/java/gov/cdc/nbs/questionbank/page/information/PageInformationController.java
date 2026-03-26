package gov.cdc.nbs.questionbank.page.information;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages/{page}")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class PageInformationController {

  private final PageInformationFinder finder;

  PageInformationController(final PageInformationFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "find",
      summary = "Returns the Page Information of a page",
      description =
          "The Page Information includes the event type, message mapping guide, name, datamart, description, and any related conditions",
      tags = "Page Information")
  @GetMapping("/information")
  Optional<PageInformation> find(@PathVariable long page) {
    return this.finder.find(page);
  }
}
