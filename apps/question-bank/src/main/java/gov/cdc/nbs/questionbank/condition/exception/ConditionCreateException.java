package gov.cdc.nbs.questionbank.condition.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ConditionCreateException extends RuntimeException {
    public ConditionCreateException(String message){
        super(message);
    }
}
