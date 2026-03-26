package gov.cdc.nbs.questionbank.option.page.name;

import gov.cdc.nbs.questionbank.option.PageBuilderOption;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/options")
class PageNameOptionsController {

  private final PageNameOptionFinder finder;

  PageNameOptionsController(final PageNameOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "pageNames",
      summary = "All Page name options",
      description = "Provides Page name options from all Pages available.",
      tags = "Page Builder Options")
  @GetMapping("/page/names")
  Collection<PageBuilderOption> all() {
    return this.finder.all();
  }
}
