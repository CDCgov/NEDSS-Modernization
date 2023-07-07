package gov.cdc.nbs.questionbank.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import gov.cdc.nbs.questionbank.question.exception.NullObjectException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;

@ControllerAdvice
public class QuestionExceptionHandler {

    @ExceptionHandler({
            CreateQuestionException.class,
            UpdateQuestionException.class,
            NullObjectException.class,
            UniqueQuestionException.class,})
    public ResponseEntity<ExceptionMessage> handleBadRequestExceptions(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleNotFoundException(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    static record ExceptionMessage(String message) {

    }


}
