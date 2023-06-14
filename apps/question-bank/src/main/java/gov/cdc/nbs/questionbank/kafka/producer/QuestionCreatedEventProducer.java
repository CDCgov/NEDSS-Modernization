package gov.cdc.nbs.questionbank.kafka.producer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionCreatedEvent;

public sealed interface QuestionCreatedEventProducer {
    void send(final QuestionCreatedEvent status);


    @Component
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "false")
    public static final class DisabledProducer implements QuestionCreatedEventProducer {

        @Override
        public void send(final QuestionCreatedEvent status) {
            // If kafka is disabled, do nothing on 'send'
        }

    }

    @Component
    @ConditionalOnProperty("kafka.enabled")
    public static final class EnabledProducer implements QuestionCreatedEventProducer {
        private final String topic;
        private final KafkaTemplate<String, QuestionCreatedEvent> template;

        public EnabledProducer(
                final KafkaTemplate<String, QuestionCreatedEvent> template,
                final RequestProperties properties) {
            this.template = template;
            this.topic = properties.questionCreated();
        }

        public void send(final QuestionCreatedEvent status) {
            template.send(topic, status);
        }
    }

}
