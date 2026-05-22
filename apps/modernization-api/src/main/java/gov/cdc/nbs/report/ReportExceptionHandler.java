package gov.cdc.nbs.report;

import gov.cdc.nbs.exception.NotFoundException;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice(assignableTypes = {ReportController.class})
public class ReportExceptionHandler {

  private static final System.Logger LOGGER =
      System.getLogger(ReportExceptionHandler.class.getName());

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFound(NotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotImplementedException.class)
  public ResponseEntity<String> handleNotImplemented(NotImplementedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleUnprocessableEntity(IllegalArgumentException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleFailedSerialization(HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  /**
   * Ensure the status code set on ResponseStatusExceptions is maintained through to the response,
   * and is not overridden with 500 through our ExceptionHandler for the base Exception class.
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
    return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleUnexpectedError(Exception ex) {
    LOGGER.log(System.Logger.Level.ERROR, ex.getMessage(), ex);
    return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
