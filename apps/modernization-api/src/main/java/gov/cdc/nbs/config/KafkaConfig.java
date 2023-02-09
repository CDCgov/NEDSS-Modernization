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
import gov.cdc.nbs.message.KafkaMessageSerializer;
import gov.cdc.nbs.message.PatientDeleteRequest;
import gov.cdc.nbs.message.PatientUpdateRequest;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;

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

	@Bean
	public ProducerFactory<String, EnvelopeRequest> producerFactoryPatientSearch() {
		if (!kafkaEnabled) {
			return new DefaultKafkaProducerFactory<>(new HashMap<>(), new StringSerializer(), new JsonSerializer<>());
		} else {
			var config = getKafkaConfig();
			return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<>());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public ProducerFactory<String, PatientUpdateRequest> producerFactoryPatientUpdate() {
		if (!kafkaEnabled) {
			return new DefaultKafkaProducerFactory<>(new HashMap<>(), new StringSerializer(), new JsonSerializer<>());
		} else {
			var config = getKafkaConfig();
			return new DefaultKafkaProducerFactory(config, new StringSerializer(), new KafkaMessageSerializer());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public ProducerFactory<String, PatientDeleteRequest> producerFactoryPatientDelete() {
		if (!kafkaEnabled) {
			return new DefaultKafkaProducerFactory<>(new HashMap<>(), new StringSerializer(), new JsonSerializer<>());
		} else {
			var config = getKafkaConfig();
			return new DefaultKafkaProducerFactory(config, new StringSerializer(), new KafkaMessageSerializer());
		}
	}

	private Map<String, Object> getKafkaConfig() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put("schema.registry.url", schemaRegistryUrl);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		 KafkaAvroSerializer kafkaAvroSerializer = new KafkaAvroSerializer();
		kafkaAvroSerializer.configure(config, false);
		
		/*KafkaJsonSchemaSerializer kafkaJsonSerializer= new KafkaJsonSchemaSerializer();
		kafkaJsonSerializer.configure(config, false);*/
		
		//JsonSerializer jsonSerializer = new JsonSerializer();
		//jsonSerializer.configure(config, false);

		return new DefaultKafkaProducerFactory(config, new StringSerializer(), new KafkaMessageSerializer());
	}

	@Bean
	public KafkaTemplate<String, PatientUpdateRequest> kafkaTemplatePatientUpdate() {
		return new KafkaTemplate<>(producerFactoryPatientUpdate());
	}

	@Bean
	public KafkaTemplate<String, EnvelopeRequest> kafkaTemplatePatientSearch() {
		return new KafkaTemplate<>(producerFactoryPatientSearch());
	}

	@Bean
	public KafkaTemplate<String, PatientDeleteRequest> kafkaTemplatePatientDelete() {
		return new KafkaTemplate<>(producerFactoryPatientDelete());
	}

	@Bean
	public CommonLoggingErrorHandler errorHandler() {
		return new CommonLoggingErrorHandler();
	}

}
