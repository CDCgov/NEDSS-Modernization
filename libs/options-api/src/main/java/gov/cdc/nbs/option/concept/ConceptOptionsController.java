package gov.cdc.nbs.option.concept;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class ConceptOptionsController {

  private final ConceptOptionFinder finder;

  ConceptOptionsController(final ConceptOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "concepts",
      summary = "Concept Options by Value Set",
      description = "Provides options from Concepts grouped into a value set.",
      tags = "ConceptOptions"
  )
  @GetMapping("nbs/api/options/concepts/{name}")
  ConceptOptionsResponse all(
      @PathVariable final String name
  ) {
    Collection<ConceptOption> found = this.finder.find(name);
    return ConceptOptionsResponseMapper.asResponse(name, found);
  }

}
