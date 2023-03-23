package gov.cdc.nbs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty("kafka.enabled")
public class KafkaConfig {

    @Bean
    NewTopic createPatientSearchTopic(
        @Value("${kafkadef.topics.patient.request}") final String topic,
        @Value("${kafka.properties.topic.partition.count}") final int topicPartitionCount,
        @Value("${kafka.properties.topic.replication.factor}") final int topicReplicationFactor
        ) {
      return TopicBuilder.name(topic)
          .partitions(topicPartitionCount)
          .replicas(topicReplicationFactor)
          .compact().build();
    }

    @Bean
    <V> KafkaTemplate<String, V> kafkaTemplate(final ProducerFactory<String, V> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    <V> ProducerFactory<String, V> producerFactory(
        final JsonSerializer<V> serializer,
        @Value("${kafka.bootstrap-servers}")
        final String bootstrapServers,
        @Value("${kafka.properties.schema.registry.url}")
        final String schemaRegistryUrl
        ) {
        return new DefaultKafkaProducerFactory<>(
            getKafkaConfig(bootstrapServers, schemaRegistryUrl),
            new StringSerializer(),
            serializer
        );
    }

    @Bean
    <T> JsonSerializer<T> kafkaMessageSerializer(final ObjectMapper mapper) {
        return new JsonSerializer<>(mapper);
    }

    private Map<String, Object> getKafkaConfig(
        final String schemaRegistryUrl,
        final String bootstrapServers
    ) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put("schema.registry.url", schemaRegistryUrl);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return config;
    }

    @Bean
    CommonLoggingErrorHandler errorHandler() {
        return new CommonLoggingErrorHandler();
    }

}
