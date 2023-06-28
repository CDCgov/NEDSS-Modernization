package gov.cdc.nbs.questionbank.page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import gov.cdc.nbs.questionbank.exception.QueryException;

@ControllerAdvice
public class PageExceptionHandler {

    @ExceptionHandler({
            QueryException.class})
    public ResponseEntity<ExceptionMessage> handleBadRequestExceptions(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private record ExceptionMessage(String message) {
    }
}
