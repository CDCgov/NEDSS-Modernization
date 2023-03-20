package gov.cdc.nbs.patientlistener.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.patientlistener.kafka.KafkaErrorHandler;
import gov.cdc.nbs.patientlistener.kafka.StatusProducer;

@Configuration
public class KafkaConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${kafkadef.groups.patient}")
    private String patientGroup;

    private static final String SCHEMA_URL = "schema.registry.url";

    // Producer

    @Bean
    <V> KafkaTemplate<String, V> kafkaTemplate(final ProducerFactory<String, V> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    <V> JsonSerializer<V> kafkaMessageSerializer(final ObjectMapper mapper) {
        return new JsonSerializer<>(mapper);
    }

    @Bean
    <V> ProducerFactory<String, V> producerFactory(final JsonSerializer<V> serializer) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(SCHEMA_URL, schemaRegistryUrl);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), serializer);
    }

    // Consumer

    @Bean("kafkaListenerContainerFactory")
    <V> ConcurrentKafkaListenerContainerFactory<String, V> concurrentKafkaListenerContainerFactory(
            KafkaErrorHandler errorHandler) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, V>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(commonConsumerConfigs()));
        factory.setCommonErrorHandler(errorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        return factory;
    }

    @Bean
    KafkaErrorHandler commonErrorHandler(StatusProducer producer) {
        return new KafkaErrorHandler(producer);
    }

    private Map<String, Object> commonConsumerConfigs() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(SCHEMA_URL, schemaRegistryUrl);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, patientGroup);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return config;
    }

}
