package gov.cdc.nbs.questionbank.condition;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import gov.cdc.nbs.questionbank.condition.request.UpdateConditionRequest;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.entity.condition.LdfPageSet;

@Service
public class ConditionUpdater {
    @Autowired
    private ConditionCodeRepository conditionCodeRepository;

    public Condition updateCondition(UpdateConditionRequest request) {
        
    }
}
