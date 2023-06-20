package gov.cdc.nbs.questionbank.valueset;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetRequest;
import gov.cdc.nbs.questionbank.valueset.response.CreateValueSetResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/valueset/")
@RequiredArgsConstructor
public class ValueSetController {
	
	 private final ValueSetCreator valueSetCreator;
	 
	 private final UserDetailsProvider userDetailsProvider;
	 
	 @PostMapping
	 @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	 public ResponseEntity<CreateValueSetResponse> createValueSet(@RequestBody  ValueSetRequest request) {
		 Long userId = userDetailsProvider.getCurrentUserDetails().getId();
		 CreateValueSetResponse response = valueSetCreator.createValueSet(request,userId);
		 return new ResponseEntity<>(response,null,response.getStatus());
		 
		 
	 }
	 
}
