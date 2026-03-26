package gov.cdc.nbs.option.states.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nbs/api/options/states/search")
class StateOptionAutocompleteController {

  private final StateOptionResolver resolver;

  StateOptionAutocompleteController(final StateOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "state-autocomplete",
      summary = "NBS State Option Autocomplete",
      description = "Provides options from Counties that have a name matching a criteria.",
      tags = "StateOptions")
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria, @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, limit);
  }
}
