package gov.cdc.nbs.option.counties.list;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CountiesListController {
  private final CountiesListFinder finder;

  CountiesListController(final CountiesListFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "counties",
      summary = "Counties Options by state",
      description = "Provides all Counties options for a specific state.",
      tags = "CountyOptions")
  @GetMapping("nbs/api/options/counties/{state}")
  Collection<Option> counties(@PathVariable final String state) {
    return finder.find(state);
  }
}
