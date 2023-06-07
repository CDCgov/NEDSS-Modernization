package gov.cdc.nbs.questionbank.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;

@Component
public class QuestionCreatedEventProducer {
    private final String statusTopic;
    private final KafkaTemplate<String, QuestionCreatedEvent> template;

    public QuestionCreatedEventProducer(
            final KafkaTemplate<String, QuestionCreatedEvent> template,
            final RequestProperties properties) {
        this.template = template;
        this.statusTopic = properties.questionCreated();
    }

    public void send(final QuestionCreatedEvent status) {
        template.send(statusTopic, status);
    }
}
