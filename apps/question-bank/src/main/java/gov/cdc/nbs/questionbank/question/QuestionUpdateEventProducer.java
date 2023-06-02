package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.UpdateTextQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionUpdateEventProducer {

    @Value("${kafkadef.topics.questionbank.deletestatus}")
    private String questionUpdateStatusTopic;

    private final KafkaTemplate<String, UpdateTextQuestionRequest> kafkaQuestionDeleteTemplate;


    public void send(final UpdateTextQuestionRequest status ) {
        kafkaQuestionDeleteTemplate.send(questionUpdateStatusTopic,status);
    }


}