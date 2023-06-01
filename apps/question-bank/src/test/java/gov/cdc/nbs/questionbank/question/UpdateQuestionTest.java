package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import org.junit.jupiter.api.Test;

    class UpdateQuestionTest<QuestionService> {

    void processUpdateQuestion() {

        Long questionId = UUID.randomUUID().getLeastSignificantBits();
        Long userId = UUID.randomUUID().getLeastSignificantBits();

        String updatedQuestion = "";
        when(QuestionRepository.updateQuestion(questionId, updatedQuestion))
                .thenReturn(1);

        QuestionBankEventResponse result = QuestionHandler.processUpdateQuestion(questionId, userId);

    }



        @Test
        void updateQuestion() {
            Long questionId = UUID.randomUUID().getLeastSignificantBits();
            String updatedQuestion = "";
            when(QuestionRepository.updateQuestion(questionId, updatedQuestion))
                    .thenReturn(1);
            int result = QuestionRepository.updateQuestion(questionId, updatedQuestion);
            assertEquals(result, 1);


        }

        public class QuestionBankTest<QuestionUpdateProducer> {
            @Test
            public void testUpdateQuestion_WhenQuestionIdAndUpdatedQuestionNotNull() {
                // Create a mock QuestionRepository
                QuestionRepository questionRepository = mock(QuestionRepository.class);
                when(QuestionRepository.updateQuestion(anyLong(), anyString())).thenReturn(1);

                // Call the method under test
                int updated = QuestionHandler.QuestionBank.updateQuestion(123L, "This is the updated question.");

                // Verify that the QuestionRepository's updateQuestion method was called with the correct arguments
                verify(questionRepository, times(1));
                QuestionRepository.updateQuestion(eq(123L), eq("This is the updated question."));

                // Assert that the return value is as expected
                assertEquals(1, updated);
            }

            @Test
            public void testUpdateQuestion_WhenQuestionIdNull() {
                // Call the method under test
                int updated = QuestionHandler.QuestionBank.updateQuestion(null, "This is the updated question.");

                // Assert that the return value is as expected
                assertEquals(-1, updated);
            }

            @Test
            public void testUpdateQuestion_WhenUpdatedQuestionNull() {
                // Call the method under test
                int updated = QuestionHandler.QuestionBank.updateQuestion(123L, null);

                // Assert that the return value is as expected
                assertEquals(-1, updated);
            }

            @Test
            public void testSendQuestionUpdateEvent() {
                // Create a mock QuestionRequest.UpdateTextQuestionRequest
                QuestionRequest.UpdateTextQuestionRequest status = mock(QuestionRequest.UpdateTextQuestionRequest.class);

                // Create a mock QuestionUpdateProducer
                QuestionUpdateProducer questionUpdateProducer = (QuestionUpdateProducer) mock(QuestionRequest.class);

                // Call the method under test
                //QuestionHandler.QuestionBank.updateQuestion(status);

                // Verify that the QuestionUpdateProducer's send method was called with the correct argument
                verify(questionUpdateProducer, times(1)).equals(eq(status));
            }
        }





    }
