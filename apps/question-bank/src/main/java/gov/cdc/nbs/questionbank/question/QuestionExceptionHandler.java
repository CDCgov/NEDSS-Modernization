package gov.cdc.nbs.questionbank.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import gov.cdc.nbs.questionbank.question.exception.NullObjectException;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;

@ControllerAdvice
public class QuestionExceptionHandler {

    @ExceptionHandler({CreateQuestionException.class, NullObjectException.class})
    public ResponseEntity<ExceptionMessage> handleQuestionCreateException(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    static record ExceptionMessage(String message) {

    }


}
