package gov.cdc.nbs.questionbank.condition.response;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

@Data
@NoArgsConstructor
public class CreateConditionResponse {
    private ConditionCodeId id;
    private String message;
    private HttpStatus status;
}

//what else needs to be added in the response payload