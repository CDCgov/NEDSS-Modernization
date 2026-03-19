package gov.cdc.nbs.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.exception.NotFoundException;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ReportExceptionHandlerTest {

  private final ReportExceptionHandler handler = new ReportExceptionHandler();

  @Test
  void should_return_error_msg_and_status_code_for_not_found() {
    NotFoundException exception = new NotFoundException("Not Found");

    ResponseEntity<String> responseEntity = handler.handleNotFound(exception);

    assertEquals("Not Found", responseEntity.getBody());
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_not_implemented() {
    NotImplementedException exception = new NotImplementedException("Not Implemented");

    ResponseEntity<String> responseEntity = handler.handleNotImplemented(exception);

    assertEquals("Not Implemented", responseEntity.getBody());
    assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
  }
}
