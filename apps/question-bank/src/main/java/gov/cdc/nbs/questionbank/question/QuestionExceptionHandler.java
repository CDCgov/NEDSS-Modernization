package gov.cdc.nbs.questionbank.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import gov.cdc.nbs.questionbank.question.exception.QuestionCreateException;

@ControllerAdvice
public class QuestionExceptionHandler {

    @ExceptionHandler(QuestionCreateException.class)
    public ResponseEntity<ExceptionMessage> handleQuestionCreateException(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private static record ExceptionMessage(String message) {

    }


}
