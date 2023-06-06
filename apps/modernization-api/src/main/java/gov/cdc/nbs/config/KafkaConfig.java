package gov.cdc.nbs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@ConditionalOnProperty("kafka.enabled")
public class KafkaConfig {

    @Bean
    DefaultKafkaProducerFactoryCustomizer defaultKafkaProducerFactoryValueSerializerCustomizer(
        final ObjectMapper mapper
    ) {
        return c -> c.setValueSerializer(new JsonSerializer<>(mapper));
    }

    @Bean
    CommonLoggingErrorHandler errorHandler() {
        return new CommonLoggingErrorHandler();
    }

}
