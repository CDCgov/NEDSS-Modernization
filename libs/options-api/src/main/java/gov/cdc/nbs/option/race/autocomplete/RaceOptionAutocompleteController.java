package gov.cdc.nbs.option.race.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class RaceOptionAutocompleteController {

  private final RaceOptionResolver resolver;

  RaceOptionAutocompleteController(final RaceOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "racesComplete",
      summary = "Race Option Autocomplete",
      description = "Provides options from Races that have a name matching a criteria.",
      tags = "RaceOptions"
  )
  @GetMapping("nbs/api/options/races/search")
  Collection<Option> complete(
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15") final int limit
  ) {
    return this.resolver.resolve(criteria, limit);
  }

}
