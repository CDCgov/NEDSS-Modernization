package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.exc.StreamConstraintsException;
import gov.cdc.nbs.exception.ForbiddenException;
import gov.cdc.nbs.exception.NotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.util.UUID;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

class ReportExceptionHandlerTest {

  private final ReportExceptionHandler handler = new ReportExceptionHandler();

  @Test
  void should_return_error_msg_and_status_code_for_method_arg_not_valid() {
    MethodParameter param = mock(MethodParameter.class);
    Executable constructor = mock(Constructor.class);
    when(param.getExecutable()).thenReturn(constructor);

    BindingResult bindingResult = mock(BindingResult.class);

    MethodArgumentNotValidException exception =
        new MethodArgumentNotValidException(param, bindingResult);

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleValidationExceptions(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_forbidden() {
    ForbiddenException exception = new ForbiddenException("Nope");

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleForbidden(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Nope", responseEntity.getBody().message());
    assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_not_found() {
    NotFoundException exception = new NotFoundException("Not Found");

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleNotFound(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Not Found", responseEntity.getBody().message());
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_not_implemented() {
    NotImplementedException exception = new NotImplementedException("Not Implemented");

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleNotImplemented(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Not Implemented", responseEntity.getBody().message());
    assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_illegal_argument() {
    IllegalArgumentException exception = new IllegalArgumentException("Illegal Argument");

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleUnprocessableEntity(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Illegal Argument", responseEntity.getBody().message());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_http_message_not_readable() {
    HttpInputMessage httpInputMessage = mock(HttpInputMessage.class);

    HttpMessageNotReadableException exception =
        new HttpMessageNotReadableException("Could not serialize", httpInputMessage);
    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleFailedSerialization(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Could not serialize", responseEntity.getBody().message());
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_expected_rest_client_exception() {
    String errorId = UUID.randomUUID().toString();

    ReportExceptionHandler.ErrorResponseBody errorResponseBody =
        new ReportExceptionHandler.ErrorResponseBody("things did not work", errorId);

    RestClientResponseException exception =
        new RestClientResponseException(
            "I failed", 500, "uh oh", null, errorResponseBody.toString().getBytes(), null);

    exception.setBodyConvertFunction(bytes -> errorResponseBody);

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleRestClientFailure(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("things did not work", responseEntity.getBody().message());
    assertEquals(errorId, responseEntity.getBody().id());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_unexpected_rest_client_exception() {
    RestClientResponseException exception =
        new RestClientResponseException(
            "I failed", 503, "uh oh", null, "it went poorly".getBytes(), null);

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleRestClientFailure(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("it went poorly", responseEntity.getBody().message());
    assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_max_size_exceeded_exception() {
    RestClientException exception =
        new RestClientException("uh oh", new StreamConstraintsException("too big!"));

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleRestClientException(exception);

    assertNotNull(responseEntity.getBody());
    assertThat(responseEntity.getBody().message())
        .contains("Returned report exceeds maximum size allowed by NBS");
    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
  }

  @Test
  void should_return_error_msg_and_status_code_for_unexpected_rest_exception() {
    RestClientException exception = new RestClientException("uh oh", new Exception("too big!"));

    ResponseEntity<ReportExceptionHandler.ErrorResponseBody> responseEntity =
        handler.handleRestClientException(exception);

    assertNotNull(responseEntity.getBody());
    assertEquals("Internal Server Error", responseEntity.getBody().message());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }
}
