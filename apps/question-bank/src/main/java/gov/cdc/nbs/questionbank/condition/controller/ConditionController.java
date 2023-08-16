package gov.cdc.nbs.questionbank.condition.controller;

import gov.cdc.nbs.questionbank.condition.ConditionReader;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.ReadConditionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.questionbank.condition.ConditionCreator;
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

    
}
