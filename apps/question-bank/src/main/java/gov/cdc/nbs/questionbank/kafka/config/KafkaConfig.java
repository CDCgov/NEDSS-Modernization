package gov.cdc.nbs.questionbank.kafka.config;

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
import gov.cdc.nbs.questionbank.kafka.exception.RequestErrorHandler;
import gov.cdc.nbs.questionbank.kafka.producer.RequestStatusProducer;

@ConditionalOnProperty("kafka.enabled")
@Configuration
@Import(RequestStatusProducer.class)
@EnableConfigurationProperties(RequestProperties.class)
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
            final RequestErrorHandler errorHandler) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, V>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

    @Bean
    RequestErrorHandler commonErrorHandler(RequestStatusProducer producer) {
        return new RequestErrorHandler(producer);
    }

    @Bean
    InitializingBean setErrorHandler(
            final RequestErrorHandler errorHandler,
            final ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        return () -> configurer.setCommonErrorHandler(errorHandler);
    }

}
