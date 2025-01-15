package gov.cdc.nbs.option.race.detailed.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class DetailedOptionAutocompleteController {

  private final DetailedRaceOptionResolver resolver;

  DetailedOptionAutocompleteController(final DetailedRaceOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "detailsComplete",
      summary = "Detailed Race Option Autocomplete",
      description = "Provides options from Detailed Race options for the given category that have a name matching a criteria.",
      tags = "RaceOptions")
  @GetMapping("nbs/api/options/races/{category}/search")
  Collection<Option> complete(
      @RequestParam final String criteria,
      @PathVariable final String category,
      @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, category, limit);
  }

}
