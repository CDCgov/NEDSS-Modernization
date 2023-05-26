package gov.cdc.nbs.questionbank.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;
import gov.cdc.nbs.questionbank.questionnaire.EntityMapper;

@Component
public class QuestionCreatedEventProducer {
    private final String statusTopic;
    private final EntityMapper entityMapper;
    private final KafkaTemplate<String, QuestionCreatedEvent> template;

    public QuestionCreatedEventProducer(
            final KafkaTemplate<String, QuestionCreatedEvent> template,
            final EntityMapper entityMapper,
            final RequestProperties properties) {
        this.template = template;
        this.entityMapper = entityMapper;
        this.statusTopic = properties.questionCreated();
    }

    public void send(DisplayElementEntity entity) {
        send(new QuestionCreatedEvent(
                entityMapper.toDisplayElement(entity),
                entity.getAudit().getAddUserId(),
                entity.getAudit().getAddTime()));
    }

    public void send(final QuestionCreatedEvent status) {
        template.send(statusTopic, status);
    }
}
