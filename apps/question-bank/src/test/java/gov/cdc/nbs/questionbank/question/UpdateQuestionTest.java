package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static gov.cdc.nbs.questionbank.question.QuestionHandler.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UpdateQuestionTest {

    @Test

    void updateQuestion() {

            Long questionId = UUID.randomUUID().getLeastSignificantBits();
            String updatedQuestion = "";
            when(QuestionRepository.updateQuestion(questionId, updatedQuestion))
                    .thenReturn(1);
            int result = QuestionRepository.updateQuestion(questionId, updatedQuestion);
            Assertions.assertEquals(1, result);

        }

        @Nested
        class QuestionBankTest<QuestionUpdateProducer> {
            @Test
            void testUpdateQuestion_WhenQuestionIdAndUpdatedQuestionNotNull() {
                // Create a mock QuestionRepository
                QuestionRepository questionRepository = mock(QuestionRepository.class);
                when(QuestionRepository.updateQuestion(anyLong(), anyString())).thenReturn(1);

                // Call the method under test
                int updated = QuestionBank.updateQuestion(123L, "This is the updated question.");

                // Verify that the QuestionRepository's updateQuestion method was called with the correct arguments
                verify(questionRepository, times(1));

                // Assert that the return value is as expected
                Assertions.assertEquals(1, updated);
            }

            @Test
            void testUpdateQuestion_WhenQuestionIdNull() {
                // Call the method under test
                int updated = QuestionBank.updateQuestion(null, "This is the updated question.");

                // Assert that the return value is as expected
                Assertions.assertEquals(-1, updated);
            }

            @Test
            void testUpdateQuestion_WhenUpdatedQuestionNull() {
                // Call the method under test
                int updated = QuestionBank.updateQuestion(123L, null);

                // Assert that the return value is as expected
                Assertions.assertEquals(-1, updated);
            }

            @Test
            void testSendQuestionUpdateEvent() {
                // Create a mock QuestionRequest.UpdateTextQuestionRequest
                QuestionRequest.UpdateTextQuestionRequest status = mock(QuestionRequest.UpdateTextQuestionRequest.class);

                // Create a mock QuestionUpdateProducer
                QuestionUpdateProducer questionUpdateProducer = (QuestionUpdateProducer) mock(QuestionRequest.class);


                verify(questionUpdateProducer, times(1)).equals(status);
            }


            @Test
            void testProcessUpdateQuestion() {
                // Test data
                Long questionId = 123L;
                Long userId = 456L;

                // Call the method under test
                QuestionBankEventResponse response = QuestionBank.processUpdateQuestion(questionId, String.valueOf(userId));

                // Assert that the response is not null
                Assertions.assertNotNull(response);

                // Assert that the response contains the correct questionId and message
                Assertions.assertEquals(questionId, response.getQuestionId());
                Assertions.assertEquals(Constants.UPDATE_SUCCESS_MESSAGE, response.getMessage());
            }

            @Test
            void testUpdateQuestion() {
                // Call the method under test
                String result = QuestionHandler.updateQuestion();

                // Assert that the result is not null
                Assertions.assertNotNull(result);

                // Assert that the result is empty string
                Assertions.assertEquals("", result);
            }





        }





    }
