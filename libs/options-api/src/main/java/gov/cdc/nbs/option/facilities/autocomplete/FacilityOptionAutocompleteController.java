package gov.cdc.nbs.option.facilities.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nbs/api/options/facilities/search")
class FacilityOptionAutocompleteController {

  private final FacilityOptionResolver resolver;

  FacilityOptionAutocompleteController(final FacilityOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "facility-autocomplete",
      summary = "NBS Facility Option Autocomplete",
      description = "Provides options from Facilities that have a name matching a criteria.",
      tags = "FacilityOptions")
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria, @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, limit);
  }
}
