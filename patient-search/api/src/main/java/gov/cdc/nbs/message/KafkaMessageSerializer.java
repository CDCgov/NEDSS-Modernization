package gov.cdc.nbs.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

@Slf4j
public class KafkaMessageSerializer implements Serializer<Object> {
	
	
	@Override
	public byte[] serialize(String s, Object o) {
		byte[] retVal = null;
		//ObjectMapper objectMapper = new ObjectMapper();
		ObjectMapper mapper = JsonMapper.builder()
			    .addModule(new JavaTimeModule())
			    .build();

		try {
			 mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
			retVal =  mapper.writeValueAsString(o).getBytes(StandardCharsets.UTF_8);
		} catch (Exception e) {
			log.error("Unable to serialize Kafka object", e);
		}
		return retVal;
	}

}
