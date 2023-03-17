package gov.cdc.nbs.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Slf4j
public class KafkaMessageSerializer<T> extends JsonSerializer<T> {

  public KafkaMessageSerializer(final ObjectMapper mapper) {
    super(mapper);
  }

  @Override
  public byte[] serialize(String s, T o) {
    byte[] retVal = null;

    try {
      retVal = super.serialize(s, o);
    } catch (Exception e) {
      log.error("Unable to serialize Kafka object", e);
    }
    return retVal;
  }

}
