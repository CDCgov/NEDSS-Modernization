package gov.cdc.nbs.report;

import com.fasterxml.jackson.databind.ObjectMapper;
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

  private static final String DEFAULT_ERROR_LOG = "Exception encountered (%s): %s";

  /** JSON-friendly wrapper for the response bodies of 4XX/5XX HTTP error responses. */
  public record ErrorResponseBody(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull String message, String id) {}

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseBody> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    return defaultExceptionHandler(
        ex, HttpStatus.UNPROCESSABLE_ENTITY, System.Logger.Level.WARNING);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ErrorResponseBody> handleForbidden(ForbiddenException ex) {
    return defaultExceptionHandler(ex, HttpStatus.FORBIDDEN, System.Logger.Level.DEBUG);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponseBody> handleNotFound(NotFoundException ex) {
    return defaultExceptionHandler(ex, HttpStatus.NOT_FOUND, System.Logger.Level.DEBUG);
  }

  @ExceptionHandler(NotImplementedException.class)
  public ResponseEntity<ErrorResponseBody> handleNotImplemented(NotImplementedException ex) {
    return defaultExceptionHandler(ex, HttpStatus.NOT_IMPLEMENTED, System.Logger.Level.DEBUG);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseBody> handleUnprocessableEntity(IllegalArgumentException ex) {
    return defaultExceptionHandler(
        ex, HttpStatus.UNPROCESSABLE_ENTITY, System.Logger.Level.WARNING);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponseBody> handleFailedSerialization(
      HttpMessageNotReadableException ex) {
    return defaultExceptionHandler(
        ex, HttpStatus.UNPROCESSABLE_ENTITY, System.Logger.Level.WARNING);
  }

  //  Currently limited to the ReportExecutionServiceClient
  @ExceptionHandler(RestClientResponseException.class)
  public ResponseEntity<ErrorResponseBody> handleRestClientFailure(RestClientResponseException ex) {
    ErrorResponseBody err = null;

    try {
      ObjectMapper mapper = new ObjectMapper();
      err = mapper.readValue(ex.getResponseBodyAsString(), ErrorResponseBody.class);
    } catch (Exception e) {
      LOGGER.log(
          System.Logger.Level.WARNING,
          "Unexpected error response shape from ReportExecutionServiceClient: %s"
              .formatted(ex.getResponseBodyAsString()),
          e);
    }

    String message =
        err != null && err.message != null ? err.message : ex.getResponseBodyAsString();
    String errorId = err != null && err.id != null ? err.id : UUID.randomUUID().toString();

    LOGGER.log(
        System.Logger.Level.WARNING,
        "Error received from rest client: %s (Status Code: %s)".formatted(err, ex.getStatusCode()),
        ex);
    return new ResponseEntity<>(new ErrorResponseBody(message, errorId), ex.getStatusCode());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseBody> handleUnexpectedError(Exception ex) {
    String errorId = UUID.randomUUID().toString();

    LOGGER.log(
        System.Logger.Level.ERROR, DEFAULT_ERROR_LOG.formatted(errorId, ex.getMessage()), ex);
    return new ResponseEntity<>(
        new ErrorResponseBody("Internal Server Error", errorId), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ReportExceptionHandler.ErrorResponseBody> defaultExceptionHandler(
      Exception e, HttpStatus httpStatus, System.Logger.Level logLevel) {
    String errorId = UUID.randomUUID().toString();
    LOGGER.log(logLevel, DEFAULT_ERROR_LOG.formatted(errorId, e.getMessage()), e);

    return new ResponseEntity<>(new ErrorResponseBody(e.getMessage(), errorId), httpStatus);
  }
}
