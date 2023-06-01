package gov.cdc.nbs.questionbank.kafka.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

@ConditionalOnProperty("kafka.enabled")
@Configuration
@EnableConfigurationProperties(RequestProperties.class)
public class KafkaConfig {

    // Producer
    @Bean
    DefaultKafkaProducerFactoryCustomizer defaultKafkaProducerFactoryValueSerializerCustomizer(
            final ObjectMapper mapper) {
        return c -> c.setValueSerializer(new JsonSerializer<>(mapper));
    }


}
