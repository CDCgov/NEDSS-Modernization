package gov.cdc.nbs.option.clinical.concept;

import gov.cdc.nbs.option.concept.ConceptOption;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClinicalConceptController {
  private final ClinicalConceptFinder finder;

  ClinicalConceptController(final ClinicalConceptFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "clinicalConcepts",
      summary = "Clinical concept options by value set",
      description = "Provides options from clinical concepts grouped into a value set.",
      tags = "ClinicalConceptOptions")
  @GetMapping("nbs/api/options/clinical/concepts/{name}")
  Collection<ConceptOption> distinctValues(@PathVariable final String name) {
    return finder.find(name);
  }
}
