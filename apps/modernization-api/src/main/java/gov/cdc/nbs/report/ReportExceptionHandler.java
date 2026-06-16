package gov.cdc.nbs.report;

import gov.cdc.nbs.exception.NotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

@ControllerAdvice(assignableTypes = {ReportController.class})
public class ReportExceptionHandler {

  private static final System.Logger LOGGER =
      System.getLogger(ReportExceptionHandler.class.getName());

  /** JSON-friendly wrapper around 4XX/5XX HTTP error responses. */
  public record ErrorResponse(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String errorMessage) {}

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(ex.getBindingResult().getAllErrors().toString()),
        HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
    return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotImplementedException.class)
  public ResponseEntity<ErrorResponse> handleNotImplemented(NotImplementedException ex) {
    return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_IMPLEMENTED);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleUnprocessableEntity(IllegalArgumentException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleFailedSerialization(
      HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(RestClientResponseException.class)
  public ResponseEntity<ErrorResponse> handleRestClientFailure(RestClientResponseException ex) {
    String err = ex.getResponseBodyAsString();
    LOGGER.log(System.Logger.Level.ERROR, "Error received from rest client: %s".formatted(err), ex);
    return new ResponseEntity<>(new ErrorResponse(err), ex.getStatusCode());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnexpectedError(Exception ex) {
    LOGGER.log(System.Logger.Level.ERROR, ex.getMessage(), ex);
    return new ResponseEntity<>(
        new ErrorResponse("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
