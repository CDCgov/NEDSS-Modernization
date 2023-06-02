package gov.cdc.nbs.questionbank.valueset;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CreateValueSetResolver {
	
	 private final UserDetailsProvider userDetailsProvider;
	 private final ValueSetService valueSetService;
	 
	 @MutationMapping()
	 @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
	 public void createValueSet(@Argument  ValueSetRequest.createValueSet request) {
		 long userId = userDetailsProvider.getCurrentUserDetails().getId();
		 valueSetService.createValueSet(userId,request);
		 
		 
	 }
}
