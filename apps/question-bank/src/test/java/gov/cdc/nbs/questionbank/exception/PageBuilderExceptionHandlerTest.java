package gov.cdc.nbs.questionbank.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.questionbank.exception.PageBuilderExceptionHandler.ExceptionMessage;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.page.summary.download.exceptions.CsvCreationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

class PageBuilderExceptionHandlerTest {

  private final PageBuilderExceptionHandler handler = new PageBuilderExceptionHandler();

  @Test
  void should_pass_message_for_create_exceptions() {
    // given a questionCreateException
    QueryException exception = new QueryException("Exception message");

    // when the exception handler returns a response
    ResponseEntity<ExceptionMessage> responseEntity = handler.handleBadRequestExceptions(exception);

    // then the message should be present
    assertEquals("Exception message", responseEntity.getBody().message());
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void should_pass_message_for_not_found_exceptions() {
    // given a questionCreateException
    PageNotFoundException exception = new PageNotFoundException(0);

    // when the exception handler returns a response
    ResponseEntity<ExceptionMessage> responseEntity = handler.handleNotFound(exception);

    // then the message should be present
    assertThat(responseEntity.getBody())
        .extracting(ExceptionMessage::message)
        .asString()
        .isEqualTo("A Page identified by 0 cannot be found");

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void should_set_403() {
    // given a questionCreateException
    AccessDeniedException exception = new AccessDeniedException("Failed");

    // when the exception handler returns a response
    ResponseEntity<ExceptionMessage> responseEntity = handler.handleAccessDenied(exception);

    // then the message should be present
    assertThat(responseEntity.getBody())
        .extracting(ExceptionMessage::message)
        .asString()
        .isEqualTo("Access denied");

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void should_internal_server() {
    // given a questionCreateException
    CsvCreationException exception = new CsvCreationException("failed");

    // when the exception handler returns a response
    ResponseEntity<ExceptionMessage> responseEntity = handler.handleInternalException(exception);

    // then the message should be present
    assertThat(responseEntity.getBody())
        .extracting(ExceptionMessage::message)
        .asString()
        .isEqualTo("failed");

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  void should_internal_server_runtime() {
    // given a questionCreateException
    RuntimeException exception = new RuntimeException("Failed");

    // when the exception handler returns a response
    ResponseEntity<ExceptionMessage> responseEntity = handler.handleRuntimeException(exception);

    // then the message should be present
    assertThat(responseEntity.getBody())
        .extracting(ExceptionMessage::message)
        .asString()
        .isEqualTo("An unexpected error has occurred");

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
