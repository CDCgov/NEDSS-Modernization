package gov.cdc.nbs.option.occupations.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class OccupationOptionAutocompleteController {

  private final OccupationOptionResolver resolver;

  OccupationOptionAutocompleteController(final OccupationOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "occupationComplete",
      summary = "Occupation Option Autocomplete",
      description = "Provides options from Occupation that have a name matching a criteria.",
      tags = "OccupationOptions"
  )
  @GetMapping("nbs/api/options/occupations/search")
  Collection<Option> complete(
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15") final int limit
  ) {
    return this.resolver.resolve(criteria, limit);
  }

}
