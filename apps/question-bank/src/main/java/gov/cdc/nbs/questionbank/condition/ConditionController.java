package gov.cdc.nbs.questionbank.condition;

import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.ConditionStatusResponse;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/conditions/")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class ConditionController {
    private final ConditionCreator conditionCreator;
    private final ConditionReader conditionReader;
    private final UserDetailsProvider userDetailsProvider;
    private final ConditionStatus conditionStatus;

    @PostMapping
    public Condition createCondition(@RequestBody CreateConditionRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return conditionCreator.createCondition(request, userId);
    }

    @GetMapping("all")
    public List<Condition> findAllConditions() {
        return conditionReader.findAllConditions();
    }

    @GetMapping
    public Page<Condition> findConditions(@PageableDefault(size = 20) Pageable pageable) {
        return conditionReader.findConditions(pageable);
    }

    @PostMapping("/search")
    public Page<Condition> searchConditions(@RequestBody ReadConditionRequest search,
            @PageableDefault(size = 20) Pageable pageable) {
        return conditionReader.searchCondition(search, pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public ConditionStatusResponse activateCondition(@PathVariable String id) {
        return conditionStatus.activateCondition(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public ConditionStatusResponse inactivateCondition(@PathVariable String id) {
       return conditionStatus.inactivateCondition(id);
    }


}
