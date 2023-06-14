package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import gov.cdc.nbs.questionbank.question.QuestionExceptionHandler.ExceptionMessage;
import gov.cdc.nbs.questionbank.question.exception.QuestionCreateException;

class QuestionExceptionHandlerTest {

    private final QuestionExceptionHandler handler = new QuestionExceptionHandler();

    @Test
    void should_pass_message_for_question_create_exceptions() {
        // given a questionCreateException
        QuestionCreateException exception = new QuestionCreateException("Exception message");

        // when the exception handler returns a response
        ResponseEntity<ExceptionMessage> responseEntity =
                handler.handleQuestionCreateException(exception);

        // then the message should be present
        assertEquals("Exception message", responseEntity.getBody().message());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
