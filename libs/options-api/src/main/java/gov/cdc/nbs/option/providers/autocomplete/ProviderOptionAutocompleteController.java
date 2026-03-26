package gov.cdc.nbs.option.providers.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nbs/api/options/providers/search")
class ProviderOptionAutocompleteController {

  private final ProviderOptionResolver resolver;

  ProviderOptionAutocompleteController(final ProviderOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "provider-autocomplete",
      summary = "NBS Provider Option Autocomplete",
      description = "Provides options from Providers that have a name matching a criteria.",
      tags = "ProviderOptions")
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria, @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, limit);
  }
}
