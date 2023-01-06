package gov.cdc.nbs.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.LoggingErrorHandler;
import org.springframework.kafka.support.serializer.JsonSerializer;

import gov.cdc.nbs.message.EnvelopeRequest;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;

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
   
   @Bean
   public NewTopic createPatientSearchTopic() {
           return TopicBuilder.name(patientSearchTopic)
                   .partitions(topicPartitionCount)
                   .replicas(topicReplicationFactor)
                   .compact()
                   .build();
   }
   
   @Bean
   public ProducerFactory<String, EnvelopeRequest> producerFactoryPatientSearch() {
       Map<String, Object> config = new HashMap<>();
       config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
       config.put("schema.registry.url", schemaRegistryUrl);
       config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
       config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

       return new DefaultKafkaProducerFactory(config, new StringSerializer(), new JsonSerializer());
   }
   
   @Bean
   public KafkaTemplate<String, EnvelopeRequest> kafkaTemplateDocusign() {
       return new KafkaTemplate<>(producerFactoryPatientSearch());
   }

   
   @Bean
   public LoggingErrorHandler errorHandler() {
       return new LoggingErrorHandler();
   }

}
