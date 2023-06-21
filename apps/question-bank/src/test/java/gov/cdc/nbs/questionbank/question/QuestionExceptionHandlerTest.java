package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import gov.cdc.nbs.questionbank.question.QuestionExceptionHandler.ExceptionMessage;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;

class QuestionExceptionHandlerTest {

    private final QuestionExceptionHandler handler = new QuestionExceptionHandler();

    @Test
    void should_pass_message_for_question_create_exceptions() {
        // given a questionCreateException
        CreateQuestionException exception = new CreateQuestionException("Exception message");

        // when the exception handler returns a response
        ResponseEntity<ExceptionMessage> responseEntity =
                handler.handleBadRequestExceptions(exception);

        // then the message should be present
        assertEquals("Exception message", responseEntity.getBody().message());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void should_set_not_found_status() {
        // given a question not found exception
        QuestionNotFoundException exception = new QuestionNotFoundException("Not found message");

        // when the exception handler returns a response
        ResponseEntity<ExceptionMessage> responseEntity =
                handler.handleNotFoundException(exception);

        // then the status not found
        assertEquals("Not found message", responseEntity.getBody().message());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
