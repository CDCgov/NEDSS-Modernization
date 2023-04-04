package gov.cdc.nbs.patientlistener.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.patientlistener.request.PatientRequestErrorHandler;
import gov.cdc.nbs.patientlistener.request.PatientRequestProperties;
import gov.cdc.nbs.patientlistener.request.PatientRequestStatusProducer;

@ConditionalOnProperty("kafka.enabled")
@Configuration
@Import(PatientRequestStatusProducer.class)
@EnableConfigurationProperties(PatientRequestProperties.class)
public class KafkaConfig {

    // Producer
    @Bean
    DefaultKafkaProducerFactoryCustomizer defaultKafkaProducerFactoryValueSerializerCustomizer(
            final ObjectMapper mapper) {
        return c -> c.setValueSerializer(new JsonSerializer<>(mapper));
    }

    // Consumer
    @Bean("kafkaListenerContainerFactory")
    <V> ConcurrentKafkaListenerContainerFactory<String, V> concurrentKafkaListenerContainerFactory(
            final DefaultKafkaConsumerFactory<String, V> consumerFactory,
            final PatientRequestErrorHandler errorHandler) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, V>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

    @Bean
    PatientRequestErrorHandler commonErrorHandler(PatientRequestStatusProducer producer) {
        return new PatientRequestErrorHandler(producer);
    }

    @Bean
    InitializingBean setErrorHandler(
            final PatientRequestErrorHandler errorHandler,
            final ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        return () -> configurer.setCommonErrorHandler(errorHandler);
    }

}
