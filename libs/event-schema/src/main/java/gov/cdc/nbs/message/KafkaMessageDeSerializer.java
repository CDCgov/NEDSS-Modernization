package gov.cdc.nbs.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Slf4j
public class KafkaMessageDeSerializer<V> extends JsonDeserializer<V> {

  public KafkaMessageDeSerializer(final ObjectMapper objectMapper) {
    super(objectMapper);
  }

  @Override
  public V deserialize(String topic, byte[] data) {

    try {

      return super.deserialize(topic, data);

    } catch (Exception e) {
      log.error("Unable to deserialize Kafka object", e);
    }
    return null;
  }

}
