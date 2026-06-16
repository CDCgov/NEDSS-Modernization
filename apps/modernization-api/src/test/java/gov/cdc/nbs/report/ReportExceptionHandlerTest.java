package gov.cdc.nbs.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import gov.cdc.nbs.exception.NotFoundException;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

class ReportExceptionHandlerTest {

  private final ReportExceptionHandler handler = new ReportExceptionHandler();

  @Test
  void should_return_error_msg_and_status_code_for_not_found() {
    NotFoundException exception = new NotFoundException("Not Found");

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleNotFound(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Not Found", responseEntity.getBody().errorMessage());
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_not_implemented() {
    NotImplementedException exception = new NotImplementedException("Not Implemented");

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleNotImplemented(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Not Implemented", responseEntity.getBody().errorMessage());
    assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_illegal_argument() {
    IllegalArgumentException exception = new IllegalArgumentException("Illegal Argument");

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleUnprocessableEntity(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Illegal Argument", responseEntity.getBody().errorMessage());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_rest_client_exception() {
    RestClientResponseException exception =
        new RestClientResponseException(
            "I failed", 503, "uh oh", null, "it went poorly".getBytes(), null);

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleRestClientFailure(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("it went poorly", responseEntity.getBody().errorMessage());
    assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
  }
}
