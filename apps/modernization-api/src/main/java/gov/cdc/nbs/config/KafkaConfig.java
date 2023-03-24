package gov.cdc.nbs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@ConditionalOnProperty("kafka.enabled")
public class KafkaConfig {

    @Bean
    NewTopic createPatientSearchTopic(
        @Value("${kafkadef.topics.patient.request}") final String topic,
        @Value("${kafka.properties.topic.partition.count:1}") final int topicPartitionCount,
        @Value("${kafka.properties.topic.replication.factor:1}") final int topicReplicationFactor
        ) {
      return TopicBuilder.name(topic)
          .partitions(topicPartitionCount)
          .replicas(topicReplicationFactor)
          .compact().build();
    }

    @Bean
    DefaultKafkaProducerFactoryCustomizer defaultKafkaProducerFactoryValueSerializerCustomizer(final ObjectMapper mapper) {
        return c -> c.setValueSerializer(new JsonSerializer<>(mapper));
    }

    @Bean
    CommonLoggingErrorHandler errorHandler() {
        return new CommonLoggingErrorHandler();
    }

}
