package gov.cdc.nbs.questionbank.question.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class QuestionCreateException extends RuntimeException {
    public QuestionCreateException(String message) {
        super(message);
    }
}
