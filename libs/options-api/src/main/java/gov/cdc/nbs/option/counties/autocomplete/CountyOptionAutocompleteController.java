package gov.cdc.nbs.option.counties.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("nbs/api/options/counties")
class CountyOptionAutocompleteController {

  private final CountyOptionResolver resolver;

  CountyOptionAutocompleteController(final CountyOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "county-autocomplete",
      summary = "NBS County Option Autocomplete",
      description = "Provides options from Counties that have a name matching a criteria.",
      tags = "CountyOptions")
  @GetMapping("/{state}/search")
  Collection<Option> complete(
      @RequestParam final String criteria,
      @PathVariable final String state,
      @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, state, limit);
  }

}
