package gov.cdc.nbs.questionbank.question;

import java.util.UUID;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.questionbank.exception.DeleteException;
import gov.cdc.nbs.questionbank.kafka.message.QuestionBankEventResponse;
import gov.cdc.nbs.questionbank.kafka.message.QuestionDeletedEvent;
import gov.cdc.nbs.questionbank.kafka.message.util.Constants;
import gov.cdc.nbs.questionbank.kafka.producer.QuestionDeletedEventProducer;
import gov.cdc.nbs.questionbank.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteQuestionService {
    private final QuestionDeletedEventProducer producer;
    private final QuestionRepository questionRepository;

    public QuestionBankEventResponse processDeleteQuestion(UUID questionId, Long userId) {
        // delete question send response to user
        deleteQuestion(questionId);
        // send delete event info to topic
        QuestionDeletedEvent event = QuestionDeletedEvent.builder().questionId(questionId).userId(userId)
                .requestId(getRequestId()).build();
        producer.send(event);
        return QuestionBankEventResponse.builder().questionId(questionId).message(Constants.DELETE_SUCCESS_MESSAGE)
                .build();
    }


    int deleteQuestion(UUID questionId) {
        int updated = -1;
        if (questionId == null)
            return updated;
        try {
            // mark question as inactive
            updated = questionRepository.deleteQuestion(questionId);
        } catch (Exception e) {
            throw new DeleteException();
        }

        return updated;

    }

    private String getRequestId() {
        return String.format(Constants.APP_ID + "_%s", UUID.randomUUID());
    }

}
