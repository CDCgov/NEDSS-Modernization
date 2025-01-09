package gov.cdc.nbs.option.countries.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("nbs/api/options/countries/search")
class CountryOptionAutocompleteController {

  private final CountryOptionResolver resolver;

  CountryOptionAutocompleteController(final CountryOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "country-autocomplete",
      summary = "NBS Country Option Autocomplete",
      description = "Provides options from Counties that have a name matching a criteria.",
      tags = "CountryOptions")
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, limit);
  }

}
