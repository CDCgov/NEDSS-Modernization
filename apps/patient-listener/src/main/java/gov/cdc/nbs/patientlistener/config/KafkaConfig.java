package gov.cdc.nbs.patientlistener.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.KafkaMessageDeSerializer;
import gov.cdc.nbs.message.KafkaMessageSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

  @Value("${kafka.properties.topic.partition.count}")
  private int topicPartitionCount;

  @Value("${kafka.properties.topic.replication.factor}")
  private int topicReplicationFactor;

  @Value("${kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${kafkadef.patient-search.topics.request.patientdelete}")
  private String patientDeleteTopic;

  // general topic
  @Value("${kafkadef.patient-search.topics.request.patient}")
  private String patientSearchTopic;

  @Value("${kafka.properties.schema.registry.url}")
  private String schemaRegistryUrl;

  @Value("${kafka.consumer.group-id}")
  private String groupId;

  private static final String SCHEMA_URL = "schema.registry.url";

  @Bean
  <V> KafkaTemplate<String, V> kafkaTemplate(final ProducerFactory<String, V> factory) {
    return new KafkaTemplate<>(factory);
  }

  @Bean
  <V> KafkaMessageSerializer<V> kafkaMessageSerializer(final ObjectMapper mapper) {
    return new KafkaMessageSerializer<>(mapper);
  }

  @Bean
  <V> ProducerFactory<String, V> producerFactory(final KafkaMessageSerializer<V> serializer) {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(SCHEMA_URL, schemaRegistryUrl);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaMessageSerializer.class);

    return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), serializer);
  }

  @Bean
  <V> KafkaMessageDeSerializer<V> kafkaDeserializer(final ObjectMapper mapper) {
    return new KafkaMessageDeSerializer<>(mapper);
  }

  @Bean
  ConsumerFactory<String, String> stringConsumerFactory() {
    Map<String, Object> config = new HashMap<>(commonConsumerConfigs());
    return new DefaultKafkaConsumerFactory<>(config);
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, String> stringConcurrentKafkaListenerContainerFactory(
      final ConsumerFactory<String, String> consumerFactory
  ) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }

  @Bean
  <V> ConsumerFactory<String, V> consumerFactory(final KafkaMessageDeSerializer<V> deserializer) {
    Map<String, Object> config = new HashMap<>(commonConsumerConfigs());

    return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
  }

  @Bean
  <V> ConcurrentKafkaListenerContainerFactory<String, V> concurrentKafkaListenerContainerFactory(
      final ConsumerFactory<String, V> consumerFactory
  ) {
    ConcurrentKafkaListenerContainerFactory<String, V> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    ContainerProperties props = factory.getContainerProperties();
    props.setAckMode(ContainerProperties.AckMode.RECORD);
    return factory;
  }

  private Map<String, Object> commonConsumerConfigs() {
    Map<String, Object> config = new HashMap<>();

    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(SCHEMA_URL, schemaRegistryUrl);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

    return config;
  }

  @Bean
  public NewTopic createPatientSearchTopic() {
    return TopicBuilder.name(patientSearchTopic).partitions(topicPartitionCount).replicas(topicReplicationFactor)
        .compact().build();
  }

}
