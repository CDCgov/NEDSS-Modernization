package gov.cdc.nbs.questionbank.condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            response.setStatus(HttpStatus.BAD_REQUEST);
            return response;
        }
        try {
            int result = conditionCodeRepository.activateCondition(id);
            response.setId(id);
            if (result == 1) {
                response.setStatus(HttpStatus.OK);
                response.setStatusCd('A');
            } else {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
        return response;
    }

    public ConditionStatusResponse inactivateCondition(String id) {
        ConditionStatusResponse response = new ConditionStatusResponse();
        if (id == null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            return response;
        }
        try {
            int result = conditionCodeRepository.inactivateCondition(id);
            response.setId(id);
            if (result == 1) {
                response.setStatus(HttpStatus.OK);
                response.setStatusCd('I');
            } else {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return response;
        }
        return response;
    }
}
