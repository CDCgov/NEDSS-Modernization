package gov.cdc.nbs.questionbank.condition.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateQuestionResponse;

@Slf4j
@RestController
@RequestMapping("api/v1/conditions/")
public class ConditionController {
    private final ConditionCreator conditionCreator;
    
    @PostMapping
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public ResponseEntity<Condition> create(@RequestBody Condition condition) {
        return ResponseEntity.ok(conditionCreator.create(condition));
    }
    
}
