package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import org.junit.jupiter.api.Test;

    class UpdateQuestionTest {

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



    }
