package gov.cdc.nbs.questionbank.valueset;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetRequest;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetUpdateRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import gov.cdc.nbs.questionbank.valueset.response.CreateValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.response.UpdatedValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetStateChangeResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/valueset/")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ValueSetController {

    private final ValueSetStateManager valueSetStateManager;
    private final ValueSetReader valueSetReador;
    private final ValueSetUpdater valueSetUpdater;
    private final ValueSetCreator valueSetCreator;
    private final ConceptUpdater conceptUpdater;
    private final UserDetailsProvider userDetailsProvider;

    @PostMapping
    public ResponseEntity<CreateValueSetResponse> createValueSet(@RequestBody ValueSetRequest request) {
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
        return valueSetReador.findAllValueSets(pageable);

    }

    @PostMapping("/update")
    public ResponseEntity<UpdatedValueSetResponse> updateValueSet(@RequestBody ValueSetUpdateRequest update) {
        UpdatedValueSetResponse response = valueSetUpdater.updateValueSet(update);
        return new ResponseEntity<>(response, null, response.getStatus());

    }

    @PostMapping("search")
    public Page<ValueSet> searchValueSet(@RequestBody ValueSetSearchRequest search,
            @PageableDefault(size = 25) Pageable pageable) {
        return valueSetReador.searchValueSearch(search, pageable);
    }

    @GetMapping("{codeSetNm}/concepts")
    public List<Concept> findConceptsByCodeSetName(@PathVariable String codeSetNm) {
        return valueSetReador.findConceptCodes(codeSetNm);
    }

    @PutMapping("{codeSetNm}/concepts/{conceptCode}")
    public Concept updateConcept(
            @PathVariable String codeSetNm,
            @PathVariable String conceptCode,
            @RequestBody UpdateConceptRequest request) {
        return conceptUpdater.update(codeSetNm, conceptCode, request);
    }

}
