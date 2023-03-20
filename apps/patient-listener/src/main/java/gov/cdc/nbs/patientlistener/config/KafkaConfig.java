package gov.cdc.nbs.patientlistener.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class KafkaConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    private static final String SCHEMA_URL = "schema.registry.url";


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

}
