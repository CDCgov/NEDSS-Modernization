package gov.cdc.nbs.questionbank.valueset.concept;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import gov.cdc.nbs.questionbank.valueset.request.CreateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/valueset/{codeSetNm}/concepts")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class ConceptController {
  private final ConceptCreator creator;
  private final ConceptUpdater updater;
  private final ConceptFinder finder;

  ConceptController(
      final ConceptCreator creator, final ConceptUpdater updater, final ConceptFinder finder) {
    this.creator = creator;
    this.updater = updater;
    this.finder = finder;
  }

  @GetMapping()
  List<Concept> findConcepts(@PathVariable String codeSetNm) {
    return finder.find(codeSetNm);
  }

  @PostMapping("/search")
  Page<Concept> searchConcepts(
      @PathVariable String codeSetNm,
      @ParameterObject @PageableDefault(size = 25, sort = "localCode") final Pageable pageable) {
    return finder.find(codeSetNm, pageable);
  }

  @PostMapping()
  Concept createConcept(
      @PathVariable String codeSetNm,
      @RequestBody CreateConceptRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return creator.create(codeSetNm, request, details.getId());
  }

  @PutMapping("{localCode}")
  Concept updateConcept(
      @PathVariable String codeSetNm,
      @PathVariable String localCode,
      @RequestBody UpdateConceptRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(codeSetNm, localCode, request, details.getId());
  }
}
