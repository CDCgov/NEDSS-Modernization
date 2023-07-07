package gov.cdc.nbs.questionbank.page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import gov.cdc.nbs.questionbank.exception.QueryException;
import gov.cdc.nbs.questionbank.page.PageExceptionHandler.ExceptionMessage;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;

class PageExceptionHandlerTest {

    private final PageExceptionHandler handler = new PageExceptionHandler();

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
        PageNotFoundException exception = new PageNotFoundException("Exception message");

        // when the exception handler returns a response
        ResponseEntity<ExceptionMessage> responseEntity =
                handler.handleNotFound(exception);

        // then the message should be present
        assertEquals("Exception message", responseEntity.getBody().message());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
