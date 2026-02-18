package gov.cdc.nbs.option.users.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nbs/api/options/users/search")
class UserOptionAutocompleteController {

  private final UserOptionResolver resolver;

  UserOptionAutocompleteController(final UserOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "user-autocomplete",
      summary = "NBS User Option Autocomplete",
      description = "Provides options from Users that have a name matching a criteria.",
      tags = "UserOptions")
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria, @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, limit);
  }
}
