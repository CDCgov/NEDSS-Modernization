package gov.cdc.nbs.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

@Slf4j
public class KafkaMessageSerializer implements Serializer<Object> {
	private final ObjectMapper objectMapper;

	public KafkaMessageSerializer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public byte[] serialize(String s, Object o) {
		byte[] retVal = null;

		try {
			objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
			retVal = objectMapper.writeValueAsString(o).getBytes(StandardCharsets.UTF_8);
		} catch (Exception e) {
			log.error("Unable to serialize Kafka object", e);
		}
		return retVal;
	}

}
