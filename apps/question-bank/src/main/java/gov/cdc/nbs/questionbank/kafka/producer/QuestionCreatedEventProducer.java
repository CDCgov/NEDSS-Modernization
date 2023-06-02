package gov.cdc.nbs.questionbank.kafka.producer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionEvent;
import gov.cdc.nbs.questionbank.questionnaire.EntityMapper;

public sealed interface QuestionCreatedEventProducer {
    void send(final QuestionCreatedEvent status);

    void send(final DisplayElementEntity entity);


    @Component
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "false")
    public static final class DisabledProducer implements QuestionCreatedEventProducer {

        @Override
        public void send(final QuestionCreatedEvent status) {
            return;
        }

        @Override
        public void send(DisplayElementEntity entity) {
            return;
        }

    }

    @Component
    @ConditionalOnProperty("kafka.enabled")
    public static final class EnabledProducer implements QuestionCreatedEventProducer {
        private final String topic;
        private final EntityMapper entityMapper;
        private final KafkaTemplate<String, QuestionEvent> template;

        public EnabledProducer(
                final KafkaTemplate<String, QuestionEvent> template,
                final EntityMapper entityMapper,
                final RequestProperties properties) {
            this.template = template;
            this.entityMapper = entityMapper;
            this.topic = properties.questionCreated();
        }

        public void send(DisplayElementEntity entity) {
            send(new QuestionCreatedEvent(
                    entityMapper.toDisplayElement(entity),
                    entity.getAudit().getAddUserId(),
                    entity.getAudit().getAddTime()));
        }

        public void send(final QuestionCreatedEvent status) {
            template.send(topic, status);
        }

    }

}
