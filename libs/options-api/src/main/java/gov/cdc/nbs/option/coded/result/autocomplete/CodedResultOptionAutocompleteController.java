package gov.cdc.nbs.option.coded.result.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("nbs/api/options/coded-result/search")
class CodedResultOptionAutocompleteController {

  private final CodedResultOptionResolver resolver;

  CodedResultOptionAutocompleteController(final CodedResultOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "coded-result-autocomplete",
      summary = "NBS Coded result Option Autocomplete",
      description = "Provides options from Local or SNOMED coded results that have a name or code matching a criteria.",
      tags = "CodedResultOptions")
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15") final int limit
  ) {
    return this.resolver.resolve(criteria, limit);
  }

}
