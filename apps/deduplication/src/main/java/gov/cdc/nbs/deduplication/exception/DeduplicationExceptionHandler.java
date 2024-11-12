package gov.cdc.nbs.deduplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DeduplicationExceptionHandler {

  @ExceptionHandler({ConfigurationParsingException.class})
  public ResponseEntity<ExceptionMessage> handleBadRequestExceptions(Exception e) {
    return new ResponseEntity<>(new ExceptionMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  record ExceptionMessage(String message) {
  }
}
