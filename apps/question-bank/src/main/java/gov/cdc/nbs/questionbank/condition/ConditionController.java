package gov.cdc.nbs.questionbank.condition;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/conditions/")
@RequiredArgsConstructor
public class ConditionController {
    private final ConditionCreator conditionCreator;
    private final UserDetailsProvider userDetailsProvider;

    @PostMapping
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public CreateConditionResponse createCondition(@RequestBody CreateConditionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return conditionCreator.createCondition(request, userId);
    }

}
