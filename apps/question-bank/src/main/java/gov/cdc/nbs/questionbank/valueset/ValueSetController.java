package gov.cdc.nbs.questionbank.valueset;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetCreateRequest;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetUpdateRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import gov.cdc.nbs.questionbank.valueset.response.CreateValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.response.UpdatedValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetOption;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetStateChangeResponse;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/valueset")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ValueSetController {

  private final ValueSetOptionFinder optionFinder;
  private final ValueSetStateManager valueSetStateManager;
  private final ValueSetReader valueSetReader;
  private final ValueSetUpdater valueSetUpdater;
  private final ValueSetCreator valueSetCreator;
  private final ConceptCreator conceptCreator;
  private final ConceptUpdater conceptUpdater;
  private final UserDetailsProvider userDetailsProvider;

  @PostMapping
  public ResponseEntity<CreateValueSetResponse> createValueSet(@RequestBody ValueSetCreateRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    CreateValueSetResponse response = valueSetCreator.createValueSet(request, userId);
    return new ResponseEntity<>(response, null, response.getStatus());

  }

  @PutMapping("{codeSetNm}/deactivate")
  public ResponseEntity<ValueSetStateChangeResponse> deleteValueSet(@PathVariable String codeSetNm) {
    ValueSetStateChangeResponse response = valueSetStateManager.deleteValueSet(codeSetNm);
    return new ResponseEntity<>(response, null, response.getStatus());
  }

  @PutMapping("{codeSetNm}/activate")
  public ResponseEntity<ValueSetStateChangeResponse> activateValueSet(@PathVariable String codeSetNm) {
    ValueSetStateChangeResponse response = valueSetStateManager.activateValueSet(codeSetNm);
    return new ResponseEntity<>(response, null, response.getStatus());
  }

  @GetMapping
  public Page<ValueSet> findAllValueSets(@PageableDefault(size = 25) Pageable pageable) {
    return valueSetReader.findAllValueSets(pageable);
  }

  @GetMapping("/options")
  public List<ValueSetOption> findValueSetOptions() {
    return optionFinder.findAllValueSetOptions();
  }

  @PostMapping("/update")
  public ResponseEntity<UpdatedValueSetResponse> updateValueSet(@RequestBody ValueSetUpdateRequest update) {
    UpdatedValueSetResponse response = valueSetUpdater.updateValueSet(update);
    return new ResponseEntity<>(response, null, response.getStatus());

  }

  @PostMapping("/options/search")
  public Page<ValueSetOption> searchValueSet(
      @PageableDefault(size = 25, sort = "name") Pageable pageable,
      @RequestBody ValueSetSearchRequest request) {
    return optionFinder.search(request, pageable);
  }

  @GetMapping("{codeSetNm}/concepts")
  public List<Concept> findConceptsByCodeSetName(@PathVariable String codeSetNm) {
    return valueSetReader.findConceptCodes(codeSetNm);
  }

  @PutMapping("/{valueSetCode}/concepts/{localCode}")
  public Concept updateConcept(
      @PathVariable String valueSetCode,
      @PathVariable String localCode,
      @RequestBody UpdateConceptRequest request,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    return conceptUpdater.update(valueSetCode, localCode, request, details.getId());
  }

  @PostMapping("{codeSetNm}/concepts")
  public Concept addConcept(@PathVariable String codeSetNm, @RequestBody AddConceptRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return conceptCreator.addConcept(codeSetNm, request, userId);
  }


}
