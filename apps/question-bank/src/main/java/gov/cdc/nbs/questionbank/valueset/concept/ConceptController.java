package gov.cdc.nbs.questionbank.valueset.concept;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.valueset.ConceptCreator;
import gov.cdc.nbs.questionbank.valueset.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.ConceptUpdater;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import gov.cdc.nbs.questionbank.valueset.request.CreateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/valueset/{codeSetNm}/concepts")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ConceptController {
  private final ConceptCreator creator;
  private final ConceptUpdater updater;
  private final ConceptFinder finder;

  public ConceptController(
      final ConceptCreator creator,
      final ConceptUpdater updater,
      final ConceptFinder finder) {
    this.creator = creator;
    this.updater = updater;
    this.finder = finder;
  }


  @GetMapping()
  public List<Concept> findConcepts(@PathVariable String codeSetNm) {
    return finder.find(codeSetNm);
  }

  @PostMapping()
  public Concept createConcept(
      @PathVariable String codeSetNm,
      @RequestBody CreateConceptRequest request,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    return creator.create(codeSetNm, request, details.getId());
  }

  @PutMapping("{localCode}")
  public Concept updateConcept(
      @PathVariable String codeSetNm,
      @PathVariable String localCode,
      @RequestBody UpdateConceptRequest request,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(codeSetNm, localCode, request, details.getId());
  }


}
