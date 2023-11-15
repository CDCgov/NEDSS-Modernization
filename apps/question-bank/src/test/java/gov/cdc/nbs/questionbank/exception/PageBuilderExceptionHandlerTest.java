package gov.cdc.nbs.questionbank.exception;

import gov.cdc.nbs.questionbank.exception.PageBuilderExceptionHandler.ExceptionMessage;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PageBuilderExceptionHandlerTest {

  private final PageBuilderExceptionHandler handler = new PageBuilderExceptionHandler();

  @Test
  void should_pass_message_for_create_exceptions() {
    // given a questionCreateException
    QueryException exception = new QueryException("Exception message");

    // when the exception handler returns a response
    ResponseEntity<ExceptionMessage> responseEntity =
        handler.handleBadRequestExceptions(exception);

    // then the message should be present
    assertEquals("Exception message", responseEntity.getBody().message());
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void should_pass_message_for_not_found_exceptions() {
    // given a questionCreateException
    PageNotFoundException exception = new PageNotFoundException(0);

    // when the exception handler returns a response
    ResponseEntity<ExceptionMessage> responseEntity =
        handler.handleNotFound(exception);

    // then the message should be present
    assertThat(responseEntity.getBody())
        .extracting(ExceptionMessage::message)
        .asString()
        .isEqualTo("A Page identified by 0 cannot be found");

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

  }
}
