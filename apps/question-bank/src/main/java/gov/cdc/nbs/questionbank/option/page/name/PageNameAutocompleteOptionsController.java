package gov.cdc.nbs.questionbank.option.page.name;

import gov.cdc.nbs.questionbank.option.PageBuilderOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/options")
class PageNameAutocompleteOptionsController {

  private final PageNameAutocompleteOptionFinder finder;

  PageNameAutocompleteOptionsController(final PageNameAutocompleteOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "pageNamesAutocomplete",
      summary = "Page name options autocomplete",
      description =
          "Provides Page name options from all Pages available that have a name starting with a term.",
      tags = "Page Builder Options")
  @GetMapping("/page/names/search")
  Collection<PageBuilderOption> all(
      @RequestParam @Parameter(name = "criteria", required = true) final String criteria,
      @RequestParam(defaultValue = "15") final int limit) {
    return this.finder.resolve(criteria, limit);
  }
}
