package gov.cdc.nbs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.KafkaMessageSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

  @Value("${kafka.properties.topic.partition.count}")
  private int topicPartitionCount;

  @Value("${kafka.properties.topic.replication.factor}")
  private int topicReplicationFactor;

  @Value("${kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafka.properties.schema.registry.url}")
  private String schemaRegistryUrl;

  // general topic
  @Value("${kafkadef.patient-search.topics.request.patient}")
  private String patientSearchTopic;

  @Value("${kafka.enabled:true}")
  private boolean kafkaEnabled;

  @Bean
  public NewTopic createPatientSearchTopic() {
    if (!kafkaEnabled) {
      return null;
    }
    return TopicBuilder.name(patientSearchTopic).partitions(topicPartitionCount).replicas(topicReplicationFactor)
        .compact().build();
  }


  private Map<String, Object> getKafkaConfig() {
    Map<String, Object> config = new HashMap<>();
    if (!kafkaEnabled) {
      return config;
    }
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put("schema.registry.url", schemaRegistryUrl);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    return config;
  }

  @Bean
  public CommonLoggingErrorHandler errorHandler() {
    return new CommonLoggingErrorHandler();
  }

  @Bean
  <V> KafkaTemplate<String, V> kafkaTemplate(final ProducerFactory<String, V> factory) {
    return new KafkaTemplate<>(factory);
  }

  @Bean
  <V> ProducerFactory<String, V> producerFactory(final KafkaMessageSerializer<V> serializer) {
    return new DefaultKafkaProducerFactory<>(getKafkaConfig(), new StringSerializer(), serializer);
  }

  @Bean
  <T> KafkaMessageSerializer<T> kafkaMessageSerializer(final ObjectMapper mapper) {
    return new KafkaMessageSerializer<>(mapper);
  }

}
