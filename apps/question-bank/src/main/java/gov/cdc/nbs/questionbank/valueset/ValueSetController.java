package gov.cdc.nbs.questionbank.valueset;

import java.util.List;

import gov.cdc.nbs.questionbank.valueset.response.*;
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
import gov.cdc.nbs.questionbank.valueset.model.ValueSetOption;
import gov.cdc.nbs.questionbank.valueset.model.Valueset;
import gov.cdc.nbs.questionbank.valueset.request.CreateValuesetRequest;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.questionbank.valueset.request.UpdateValueSetRequest;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetStateChangeResponse;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/valueset")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ValueSetController {

  private final ValueSetFinder finder;
  private final ValueSetOptionFinder optionFinder;
  private final ValueSetStateManager valueSetStateManager;
  private final ValueSetUpdater updater;
  private final CountyFinder countyFinder;
  private final ValueSetCreator valueSetCreator;

  public ValueSetController(
      final ValueSetFinder finder,
      final ValueSetOptionFinder optionFinder,
      final ValueSetStateManager valueSetStateManager,
      final ValueSetUpdater updater,
      final ValueSetCreator valueSetCreator,
      final CountyFinder countyFinder) {
    this.finder = finder;
    this.optionFinder = optionFinder;
    this.valueSetStateManager = valueSetStateManager;
    this.updater = updater;
    this.valueSetCreator = valueSetCreator;
    this.countyFinder = countyFinder;
  }

  @GetMapping("{codeSetNm}")
  public Valueset getValueset(@PathVariable String codeSetNm) {
    return finder.find(codeSetNm);
  }

  @PostMapping
  public Valueset create(
      @RequestBody CreateValuesetRequest request,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    return valueSetCreator.create(request, details.getId());
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

  @GetMapping("/options")
  public List<ValueSetOption> findValueSetOptions() {
    return optionFinder.findAllValueSetOptions();
  }

  @PutMapping("{codeSetNm}")
  public Valueset updateValueSet(
      @PathVariable String codeSetNm,
      @RequestBody UpdateValueSetRequest request) {
    return updater.update(codeSetNm, request);
  }

  @PostMapping("/options/search")
  public Page<ValueSetOption> searchValueSet(
      @PageableDefault(size = 25, sort = "name") Pageable pageable,
      @RequestBody ValueSetSearchRequest request) {
    return optionFinder.search(request, pageable);
  }

  @GetMapping("{stateCode}/counties")
  public List<County> findCountyByStateCode(@PathVariable String stateCode) {
    return countyFinder.findByStateCode(stateCode);
  }

}
