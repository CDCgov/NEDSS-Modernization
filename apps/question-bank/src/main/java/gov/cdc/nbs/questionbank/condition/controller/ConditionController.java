package gov.cdc.nbs.questionbank.condition.controller;

import org.springframework.http.ResponseEntity;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import gov.cdc.nbs.questionbank.condition.ConditionCreator;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;

@Slf4j
@RestController
@RequestMapping("api/v1/condition/")
@RequiredArgsConstructor
public class ConditionController {
    private final ConditionCreator conditionCreator;
    private final UserDetailsProvider userDetailsProvider;
    
    @PostMapping
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public ResponseEntity<CreateConditionResponse> createCondition(@RequestBody CreateConditionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        CreateConditionResponse createConditionResponse = conditionCreator.createCondition(request, userId);
        return new ResponseEntity<>(createConditionResponse, null, createConditionResponse.getStatus());
    }
    
}
