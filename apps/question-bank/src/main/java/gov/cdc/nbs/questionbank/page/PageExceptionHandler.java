package gov.cdc.nbs.questionbank.page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import gov.cdc.nbs.questionbank.exception.QueryException;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;

@ControllerAdvice
public class PageExceptionHandler {

    @ExceptionHandler({
            QueryException.class,
            PageUpdateException.class})
    public ResponseEntity<ExceptionMessage> handleBadRequestExceptions(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            PageNotFoundException.class})
    public ResponseEntity<ExceptionMessage> handleNotFound(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    record ExceptionMessage(String message) {
    }
}
