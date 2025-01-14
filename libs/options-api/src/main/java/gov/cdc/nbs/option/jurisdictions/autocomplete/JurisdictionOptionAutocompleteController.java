package gov.cdc.nbs.option.jurisdictions.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("nbs/api/options/jurisdictions/search")
class JurisdictionOptionAutocompleteController {

  private final JurisdictionOptionResolver resolver;

  JurisdictionOptionAutocompleteController(final JurisdictionOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "jurisdiction-autocomplete",
      summary = "NBS Jurisdiction Option Autocomplete",
      description = "Provides options from Facilities that have a name matching a criteria.",
      tags = "JurisdictionOptions"
  )
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15"
      ) final int limit) {
    return this.resolver.resolve(criteria, limit);
  }

}
