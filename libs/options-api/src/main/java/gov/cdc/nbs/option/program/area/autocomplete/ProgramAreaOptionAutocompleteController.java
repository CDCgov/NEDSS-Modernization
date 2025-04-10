package gov.cdc.nbs.option.program.area.autocomplete;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("nbs/api/options/program-areas/search")
class ProgramAreaOptionAutocompleteController {

  private final ProgramAreaOptionResolver resolver;

  ProgramAreaOptionAutocompleteController(final ProgramAreaOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "program-area-autocomplete",
      summary = "NBS Program Area Option Autocomplete",
      description = "Provides options from Facilities that have a name matching a criteria.",
      tags = "ProgramAreaOptions"
  )
  @GetMapping
  Collection<Option> complete(
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15") final int limit
  ) {
    return this.resolver.resolve(criteria, limit);
  }

}
