package gov.cdc.nbs.questionbank.condition;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

@Service
public class ConditionCreator {
    private ConditionCodeRepository conditionCodeRepository;

    //need to look over legacy codebase for additional implementations

    public CreateConditionResponse createConditionResponse(CreateConditionRequest request){
        CreateConditionResponse response = new CreateConditionResponse();


       try {
            request.setConditionCode().setId(ConditionCodeId id);
            //unfinished condition code
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.HTTP_INTERNAL_SERVER_ERROR);
        }
    }
    
}
