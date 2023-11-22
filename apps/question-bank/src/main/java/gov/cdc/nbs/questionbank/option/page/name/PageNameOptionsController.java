package gov.cdc.nbs.questionbank.option.page.name;

import gov.cdc.nbs.questionbank.option.PageBuilderOption;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
      tags = "Page Builder Options"
  )
  @ApiOperation(value = "", nickname = "pageNames")
  @GetMapping("/page/names")
  Collection<PageBuilderOption> all() {
    return this.finder.all();
  }

}
