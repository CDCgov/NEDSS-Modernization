package gov.cdc.nbs.questionbank.condition;

import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.ConditionStatusResponse;
import gov.cdc.nbs.questionbank.condition.response.ReadConditionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/conditions/")
@RequiredArgsConstructor
public class ConditionController {
    private final ConditionCreator conditionCreator;
    private final ConditionReader conditionReader;
    private final UserDetailsProvider userDetailsProvider;
    private final ConditionStatus conditionStatus;

    @PostMapping
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public CreateConditionResponse createCondition(@RequestBody CreateConditionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return conditionCreator.createCondition(request, userId);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Page<ReadConditionResponse.GetCondition> findConditions(@PageableDefault(size = 20) Pageable pageable) {
        return conditionReader.findConditions(pageable);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Page<ReadConditionResponse.GetCondition> searchConditions(@RequestBody ReadConditionRequest search, @PageableDefault(size = 20) Pageable pageable) {
        return conditionReader.searchCondition(search, pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public ConditionStatusResponse activateCondition(@PathVariable String id) {
        ConditionStatusResponse response = conditionStatus.activateCondition(id);
        return response;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public ConditionStatusResponse inactivateCondition(@PathVariable String id) {
        ConditionStatusResponse response = conditionStatus.inactivateCondition(id);
        return response;
    }


}
