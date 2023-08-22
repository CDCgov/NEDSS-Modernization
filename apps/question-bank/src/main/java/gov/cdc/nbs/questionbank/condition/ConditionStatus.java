package gov.cdc.nbs.questionbank.condition;

import gov.cdc.nbs.questionbank.condition.exception.ConditionBadRequest;
import gov.cdc.nbs.questionbank.condition.exception.ConditionInternalServerError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.response.ConditionStatusResponse;

@Service
@RequiredArgsConstructor
public class ConditionStatus {
    @Autowired
    private ConditionCodeRepository conditionCodeRepository;

    public ConditionStatusResponse activateCondition(String id) {
        ConditionStatusResponse response = new ConditionStatusResponse();
        if (id == null) {
            throw new ConditionBadRequest(null);
        }
        try {
            int result = conditionCodeRepository.activateCondition(id);
            response.setId(id);
            if (result == 1) {
                response.setStatusCd('A');
            } else {
                throw new ConditionInternalServerError(id);
            }
        } catch (Exception e) {
            throw new ConditionInternalServerError(id);
        }
        return response;
    }

    public ConditionStatusResponse inactivateCondition(String id) {
        ConditionStatusResponse response = new ConditionStatusResponse();
        if (id == null) {
            throw new ConditionInternalServerError(null);
        }
        try {
            int result = conditionCodeRepository.inactivateCondition(id);
            response.setId(id);
            if (result == 1) {
                response.setStatusCd('I');
            } else {
                throw new ConditionInternalServerError(id);
            }
        } catch (Exception e) {
            throw new ConditionInternalServerError(id);
        }
        return response;
    }
}
