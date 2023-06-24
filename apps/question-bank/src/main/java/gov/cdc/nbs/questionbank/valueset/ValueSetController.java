package gov.cdc.nbs.questionbank.valueset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetRequest;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.questionbank.valueset.response.CreateValueSetResponse;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetStateChangeResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/valueset/")
@RequiredArgsConstructor
public class ValueSetController {
	
	 private final ValueSetCreator valueSetCreator;
	 
	 private final UserDetailsProvider userDetailsProvider;

	 private final ValueSetStateManager valueSetStateManager;
	 
	 private final ValueSetReader valueSetReador;
	 
	 @PostMapping
	 @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	 public ResponseEntity<CreateValueSetResponse> createValueSet(@RequestBody  ValueSetRequest request) {
		 Long userId = userDetailsProvider.getCurrentUserDetails().getId();
		 CreateValueSetResponse response = valueSetCreator.createValueSet(request,userId);
		 return new ResponseEntity<>(response,null,response.getStatus());
		 
		 
	 }
	 

	@DeleteMapping("/{codeSetNm}")
	@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	public ResponseEntity<ValueSetStateChangeResponse> deleteValueSet(@PathVariable String codeSetNm) {
		ValueSetStateChangeResponse response = valueSetStateManager.deleteValueSet(codeSetNm);
		return new ResponseEntity<>(response, null, response.getStatus());
	}
	
	@PatchMapping("/{codeSetNm}")
	@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	public ResponseEntity<ValueSetStateChangeResponse> activateValueSet(@PathVariable String codeSetNm) {
		ValueSetStateChangeResponse response = valueSetStateManager.activateValueSet(codeSetNm);
		return new ResponseEntity<>(response, null, response.getStatus());
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	public Page<ValueSetCommand.GetValueSet> findAllValueSets(@PageableDefault(size = 25) Pageable pageable)  {
		return valueSetReador.findAllValueSets(pageable);
		
	}
	
	@PostMapping("/search")
	@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	public Page<ValueSetCommand.GetValueSet> searchValueSearch(@RequestBody ValueSetSearchRequest search, @PageableDefault(size = 25) Pageable pageable)  {
		return valueSetReador.searchValueSearch(search,pageable);
		
	}
	
	
	
	
	 
}
