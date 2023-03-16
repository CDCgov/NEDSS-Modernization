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
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import gov.cdc.nbs.message.KafkaMessageDeSerializer;
import gov.cdc.nbs.message.KafkaMessageSerializer;
import gov.cdc.nbs.message.PatientUpdateEvent;
import gov.cdc.nbs.message.PatientUpdateEventResponse;



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
	
	private static String sCHEMAURL = "schema.registry.url";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public ProducerFactory<String, PatientUpdateEventResponse> producerFactoryPatientUpdateResponse() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(sCHEMAURL, schemaRegistryUrl);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaMessageSerializer.class);

		return new DefaultKafkaProducerFactory(config, new StringSerializer(),
				new KafkaMessageSerializer());
	}

	@Bean
	public KafkaTemplate<String, PatientUpdateEventResponse> kafkaTemplatePatientUpdateResponse() {
		return new KafkaTemplate<>(producerFactoryPatientUpdateResponse());
	}

	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.putAll(commonConsumerConfigs());
		return new DefaultKafkaConsumerFactory<>(config);
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ConsumerFactory<String, PatientUpdateEvent> consumerFactoryPatientUpdate() {
		Map<String, Object> config = new HashMap<>();
		config.putAll(commonConsumerConfigs());

		KafkaMessageDeSerializer deserializer = new KafkaMessageDeSerializer();

		return new DefaultKafkaConsumerFactory(config, new StringDeserializer(), deserializer);
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, PatientUpdateEvent> kafkaPatientUpdateListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, PatientUpdateEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryPatientUpdate());
		ContainerProperties props = factory.getContainerProperties();
		props.setAckMode(ContainerProperties.AckMode.RECORD);
		return factory;
	}

	private Map<String, Object> commonConsumerConfigs() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(sCHEMAURL, schemaRegistryUrl);
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
