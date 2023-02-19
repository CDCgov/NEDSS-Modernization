package gov.cdc.nbs.patientlistener.message;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaMessageSerializer implements Serializer<Object> {
	
	
	@Override
	public byte[] serialize(String s, Object o) {
		byte[] retVal = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			 retVal = mapper.writeValueAsBytes(o);
		} catch (Exception e) {
			log.error("Unable to serialize Kafka object", e);
		}
		return retVal;
	}

}
