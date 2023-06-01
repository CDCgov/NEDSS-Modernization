package gov.cdc.nbs.questionbank.question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuestionUpdateEventProducer {

    @Value("${kafkadef.topics.questionbank.deletestatus}")
    private String questionUpdateStatusTopic;

    private final KafkaTemplate<String, QuestionRequest.UpdateTextQuestionRequest> kafkaQuestionDeleteTemplate;


    public void send(final QuestionRequest.UpdateTextQuestionRequest status ) {
        kafkaQuestionDeleteTemplate.send(questionUpdateStatusTopic,status);
    }


}