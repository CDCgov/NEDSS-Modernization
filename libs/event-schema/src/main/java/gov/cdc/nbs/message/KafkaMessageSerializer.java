package gov.cdc.nbs.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.kafka.common.serialization.Serializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaMessageSerializer implements Serializer<Object> {
	
	
	@Override
	public byte[] serialize(String s, Object o) {
		byte[] retVal = null;
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

		try {
			 retVal = mapper.writeValueAsBytes(o);
		} catch (Exception e) {
			log.error("Unable to serialize Kafka object", e);
		}
		return retVal;
	}

}
