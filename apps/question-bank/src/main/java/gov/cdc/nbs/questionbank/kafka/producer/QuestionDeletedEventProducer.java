package gov.cdc.nbs.questionbank.kafka.producer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.QuestionDeletedEvent;

public sealed interface QuestionDeletedEventProducer {
    void send(final QuestionDeletedEvent status);

    @Component
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "false")
    public static final class DisabledProducer implements QuestionDeletedEventProducer {

        @Override
        public void send(QuestionDeletedEvent status) {
            return;
        }

    }

    @Component
    @ConditionalOnProperty("kafka.enabled")
    public static final class EnabledProducer implements QuestionDeletedEventProducer {
        private final String topic;
        private final KafkaTemplate<String, QuestionDeletedEvent> template;

        public EnabledProducer(KafkaTemplate<String, QuestionDeletedEvent> template,
                final RequestProperties properties) {
            this.template = template;
            this.topic = properties.questionDeleted();
        }

        @Override
        public void send(final QuestionDeletedEvent status) {
            template.send(topic, status);
        }
    }
}
