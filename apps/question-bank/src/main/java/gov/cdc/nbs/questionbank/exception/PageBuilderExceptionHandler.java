package gov.cdc.nbs.questionbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PageBuilderExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionMessage> handleBadRequestExceptions(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            NotFoundException.class})
    public ResponseEntity<ExceptionMessage> handleNotFound(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            InternalServerException.class})
    public ResponseEntity<ExceptionMessage> handleInternalException(Exception e) {
        return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    record ExceptionMessage(String message) {
    }
}