package gov.cdc.nbs.questionbank.kafka.producer;

import gov.cdc.nbs.questionbank.kafka.config.RequestProperties;
import gov.cdc.nbs.questionbank.kafka.message.rule.RuleCreatedEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

public interface RuleCreatedEventProducer {
    void send(final RuleCreatedEvent status);

    @Component
    @ConditionalOnProperty(name = "kafka.enabled", havingValue = "false")
    public static final class DisabledProducer implements RuleCreatedEventProducer {

        @Override
        public void send(final RuleCreatedEvent status) {
            // If kafka is disabled, do nothing on 'send'
        }

    }

    @Component
    @ConditionalOnProperty("kafka.enabled")
    public static final class EnabledProducer implements RuleCreatedEventProducer {
        private final String topic;
        private final KafkaTemplate<String, RuleCreatedEvent> template;

        public EnabledProducer(
                final KafkaTemplate<String, RuleCreatedEvent> template,
                final RequestProperties properties) {
            this.template = template;
            this.topic = properties.ruleCreated();
        }

        public void send(final RuleCreatedEvent status) {
            template.send(topic, status );
        }
    }
}
