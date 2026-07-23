package gov.cdc.nbs.report;

import gov.cdc.nbs.exception.ForbiddenException;
import gov.cdc.nbs.exception.NotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
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

  /** JSON-friendly wrapper for the response bodies of 4XX/5XX HTTP error responses. */
  public record ErrorResponseBody(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String message, String id) {}

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseBody> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    String errorId = UUID.randomUUID().toString();

    return new ResponseEntity<>(
        new ErrorResponseBody(ex.getBindingResult().getAllErrors().toString(), errorId),
        HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponseBody> handleForbidden(ForbiddenException ex) {
    String errorId = UUID.randomUUID().toString();

    return new ResponseEntity<>(
        new ErrorResponseBody(ex.getMessage(), errorId), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponseBody> handleNotFound(NotFoundException ex) {
    String errorId = UUID.randomUUID().toString();

    return new ResponseEntity<>(
        new ErrorResponseBody(ex.getMessage(), errorId), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotImplementedException.class)
  public ResponseEntity<ErrorResponseBody> handleNotImplemented(NotImplementedException ex) {
    String errorId = UUID.randomUUID().toString();

    return new ResponseEntity<>(
        new ErrorResponseBody(ex.getMessage(), errorId), HttpStatus.NOT_IMPLEMENTED);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseBody> handleUnprocessableEntity(IllegalArgumentException ex) {
    String errorId = UUID.randomUUID().toString();

    return new ResponseEntity<>(
        new ErrorResponseBody(ex.getMessage(), errorId), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponseBody> handleFailedSerialization(
      HttpMessageNotReadableException ex) {
    String errorId = UUID.randomUUID().toString();

    return new ResponseEntity<>(
        new ErrorResponseBody(ex.getMessage(), errorId), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(RestClientResponseException.class)
  public ResponseEntity<ErrorResponseBody> handleRestClientFailure(RestClientResponseException ex) {
    ErrorResponseBody err = ex.getResponseBodyAs(ErrorResponseBody.class);

    String message = err != null && err.message != null ? err.message : "";
    String errorId = err != null && err.id != null ? err.id : UUID.randomUUID().toString();

    LOGGER.log(
        System.Logger.Level.ERROR,
        "Error received from rest client: %s (Status Code: %s)".formatted(err, ex.getStatusCode()),
        ex);
    return new ResponseEntity<>(new ErrorResponseBody(message, errorId), ex.getStatusCode());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseBody> handleUnexpectedError(Exception ex) {
    String errorId = UUID.randomUUID().toString();

    LOGGER.log(System.Logger.Level.ERROR, errorId, ex.getMessage(), ex);
    return new ResponseEntity<>(
        new ErrorResponseBody("Internal Server Error", errorId), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
