package gov.cdc.nbs.config;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.kafka.support.serializer.JsonSerializer;

import gov.cdc.nbs.message.EnvelopeRequest;

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

    @Value("${kafka.enabled}")
    private Boolean kafkaEnabled;

    @Bean
    public NewTopic createPatientSearchTopic() {
        if (!kafkaEnabled) {
            return null;
        }
        return TopicBuilder.name(patientSearchTopic)
                .partitions(topicPartitionCount)
                .replicas(topicReplicationFactor)
                .compact()
                .build();
    }

    @Bean
    public ProducerFactory<String, EnvelopeRequest> producerFactoryPatientSearch() {
        if (!kafkaEnabled) {
            return new DefaultKafkaProducerFactory<String, EnvelopeRequest>(new HashMap<>(), new StringSerializer(),
                    new JsonSerializer<EnvelopeRequest>());
        } else {
            Map<String, Object> config = new HashMap<>();
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            config.put("schema.registry.url", schemaRegistryUrl);
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

            return new DefaultKafkaProducerFactory<String, EnvelopeRequest>(config, new StringSerializer(),
                    new JsonSerializer<EnvelopeRequest>());
        }
    }

    @Bean
    public KafkaTemplate<String, EnvelopeRequest> kafkaTemplateDocusign() {
        return new KafkaTemplate<>(producerFactoryPatientSearch());
    }

    @Bean
    public CommonLoggingErrorHandler errorHandler() {
        return new CommonLoggingErrorHandler();
    }

}
