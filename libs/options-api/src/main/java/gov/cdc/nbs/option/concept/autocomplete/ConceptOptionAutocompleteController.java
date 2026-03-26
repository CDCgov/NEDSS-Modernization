package gov.cdc.nbs.option.concept.autocomplete;

import gov.cdc.nbs.option.concept.ConceptOption;
import gov.cdc.nbs.option.concept.ConceptOptionsResponse;
import gov.cdc.nbs.option.concept.ConceptOptionsResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ConceptOptionAutocompleteController {

  private final ConceptOptionResolver resolver;

  ConceptOptionAutocompleteController(final ConceptOptionResolver finder) {
    this.resolver = finder;
  }

  @Operation(
      operationId = "concept-search",
      summary = "Concept Option Autocomplete",
      description =
          "Provides options from Concepts grouped into a value set that have a name matching a criteria.",
      tags = "ConceptOptions")
  @GetMapping("nbs/api/options/concepts/{name}/search")
  ConceptOptionsResponse specific(
      @PathVariable final String name,
      @RequestParam final String criteria,
      @RequestParam(defaultValue = "15") final int limit) {
    Collection<ConceptOption> found = this.resolver.resolve(name, criteria, limit);
    return ConceptOptionsResponseMapper.asResponse(name, found);
  }
}
