package gov.cdc.nbs.patientlistener.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.patientlistener.request.PatientRequestErrorHandler;
import gov.cdc.nbs.patientlistener.request.PatientRequestProperties;
import gov.cdc.nbs.patientlistener.request.PatientRequestStatusProducer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty("kafka.enabled")
@Configuration
@Import(PatientRequestStatusProducer.class)
@EnableConfigurationProperties(PatientRequestProperties.class)
public class KafkaConfig {
  private static final String SCHEMA_URL = "schema.registry.url";

  // Producer

  @Bean
  DefaultKafkaProducerFactoryCustomizer defaultKafkaProducerFactoryValueSerializerCustomizer(
      final ObjectMapper mapper) {
    return c -> c.setValueSerializer(new JsonSerializer<>(mapper));
  }

  // Consumer
//  @Bean("kafkaListenerContainerFactory")
//  <V> ConcurrentKafkaListenerContainerFactory<String, V> concurrentKafkaListenerContainerFactory(
//      final DefaultKafkaConsumerFactory<String, V> consumerFactory,
//      final PatientRequestErrorHandler errorHandler
//  ) {
//    var factory = new ConcurrentKafkaListenerContainerFactory<String, V>();
//    factory.setConsumerFactory(consumerFactory);
//    factory.setCommonErrorHandler(errorHandler);
//    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
//    return factory;
//  }

  @Bean
  PatientRequestErrorHandler commonErrorHandler(PatientRequestStatusProducer producer) {
    return new PatientRequestErrorHandler(producer);
  }

  @Bean
  InitializingBean grrr(
      final PatientRequestErrorHandler errorHandler,
      final ConcurrentKafkaListenerContainerFactoryConfigurer configurer
  ) {
    return () -> configurer.setCommonErrorHandler(errorHandler);
  }

  private Map<String, Object> commonConsumerConfigs() {
    Map<String, Object> config = new HashMap<>();

//    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//    config.put(SCHEMA_URL, schemaRegistryUrl);
//    config.put(ConsumerConfig.GROUP_ID_CONFIG, patientGroup);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

    return config;
  }

}
