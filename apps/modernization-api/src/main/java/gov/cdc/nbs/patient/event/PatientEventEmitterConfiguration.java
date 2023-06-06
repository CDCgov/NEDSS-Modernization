package gov.cdc.nbs.patient.event;

import gov.cdc.nbs.message.patient.event.PatientEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
class PatientEventEmitterConfiguration {

    @Bean
    @ConditionalOnProperty("kafka.enabled")
    PatientEventEmitter kafkaPatientEventEmitter(
        @Value("${kafkadef.topics.patient.request}") final String topic,
        final KafkaTemplate<String, PatientEvent> template
    ) {
        return new KafkaPatientEventEmitter(topic, template);
    }

    @Bean
    @ConditionalOnMissingBean(PatientEventEmitter.class)
    PatientEventEmitter defaultPatientEventEmitter() {
        return event -> {
        };
    }
}
