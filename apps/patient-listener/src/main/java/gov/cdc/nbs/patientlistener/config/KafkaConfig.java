package gov.cdc.nbs.patientlistener.config;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
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

    @Value("${kafkadef.patient-search.topics.request.patientdelete}")
    private String patientDeleteTopic;

    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // Producer config

    @Bean
    public NewTopic createPatientSearchTopic() {
        return TopicBuilder.name(patientSearchTopic).partitions(topicPartitionCount).replicas(topicReplicationFactor)
                .compact().build();
    }

    @Bean
    public <T> KafkaTemplate<String, T> kafkaTemplatePatientUpdate() {
        return buildKafkaTemplate();
    }

    private <T> KafkaTemplate<String, T> buildKafkaTemplate() {
        var config = getKafkaConfig();
        return new KafkaTemplate<>(
                new DefaultKafkaProducerFactory<>(config, new StringSerializer(),
                        new JsonSerializer<>()));
    }

    private Map<String, Object> getKafkaConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put("schema.registry.url", schemaRegistryUrl);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return config;
    }
}