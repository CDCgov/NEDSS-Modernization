package gov.cdc.nbs.questionbank.question.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CreateQuestionException extends RuntimeException {
    public CreateQuestionException(String message) {
        super(message);
    }
}
