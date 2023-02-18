package gov.cdc.nbs.patientlistener.message;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaMessageDeSerializer implements Deserializer<Object> {

	@Override
	public Object deserialize(String topic, byte[] data) {

		try {
			ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

			PatientUpdateEvent event = mapper.readValue(data, PatientUpdateEvent.class);

			return event;
		} catch (Exception e) {
			log.error("Unable to deserialize Kafka object", e);
		}
		return null;
	}

}
