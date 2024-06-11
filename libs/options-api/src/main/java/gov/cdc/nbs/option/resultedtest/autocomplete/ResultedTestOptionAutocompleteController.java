package gov.cdc.nbs.option.resultedtest.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("nbs/api/options/resulted-tests/search")
class ResultedTestOptionAutocompleteController {

  private final ResultedTestOptionResolver resolver;

  ResultedTestOptionAutocompleteController(final ResultedTestOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "resultedtest-autocomplete",
      summary = "NBS Resulted Test Option Autocomplete",
      description = "Provides options from Resulted Tests that have a name matching a criteria.",
      tags = "ResultedTestOptions")
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15") final int limit) {
    return this.resolver.resolve(criteria, limit);
  }

}
