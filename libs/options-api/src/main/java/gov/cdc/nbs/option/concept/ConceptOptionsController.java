package gov.cdc.nbs.option.concept;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ConceptOptionsController {

  private final ConceptOptionFinder finder;

  ConceptOptionsController(final ConceptOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "addressUses",
      summary = "Concept Options by Value Set",
      description =
          "Provides address type options from the NBS Entity Locator Use Postal for Patients value set.",
      tags = "ConceptOptions")
  @GetMapping("nbs/api/options/concepts/EL_USE_PST_PAT")
  ConceptOptionsResponse addressUses() {
    Collection<ConceptOption> found = this.finder.find("EL_USE_PST_PAT", "BIR", "DTH");
    return ConceptOptionsResponseMapper.asResponse("EL_USE_PST_PAT", found);
  }

  @Operation(
      operationId = "concepts",
      summary = "Concept Options by Value Set",
      description = "Provides options from Concepts grouped into a value set.",
      tags = "ConceptOptions")
  @GetMapping("nbs/api/options/concepts/{name}")
  ConceptOptionsResponse all(@PathVariable final String name) {
    Collection<ConceptOption> found = this.finder.find(name);
    return ConceptOptionsResponseMapper.asResponse(name, found);
  }
}
