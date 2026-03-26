package gov.cdc.nbs.option.condition.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ConditionOptionAutocompleteController {

  private final ConditionOptionResolver resolver;

  ConditionOptionAutocompleteController(final ConditionOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "complete",
      summary = "Condition Option Autocomplete",
      description = "Provides options from Conditions that have a name matching a criteria.",
      tags = "ConditionOptions")
  @GetMapping("nbs/api/options/conditions/search")
  Collection<Option> complete(
      @RequestParam final String criteria, @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, limit);
  }
}
