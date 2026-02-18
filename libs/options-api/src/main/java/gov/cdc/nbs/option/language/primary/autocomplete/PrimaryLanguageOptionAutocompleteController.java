package gov.cdc.nbs.option.language.primary.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PrimaryLanguageOptionAutocompleteController {

  private final PrimaryLanguageOptionResolver resolver;

  PrimaryLanguageOptionAutocompleteController(final PrimaryLanguageOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "primaryLanguageComplete",
      summary = "Primary language Option Autocomplete",
      description = "Provides options from Primary languages that have a name matching a criteria.",
      tags = "PrimaryLanguageOptions")
  @GetMapping("nbs/api/options/languages/primary/search")
  Collection<Option> complete(
      @RequestParam final String criteria, @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, limit);
  }
}
