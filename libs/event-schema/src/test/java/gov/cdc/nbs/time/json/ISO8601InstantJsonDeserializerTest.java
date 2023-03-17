package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ISO8601InstantJsonDeserializerTest {

  @Test
  void should_deserialize_ISO_8601_formatted_string_to_Instant() throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(
        new SimpleModule()
            .addDeserializer(Instant.class, new ISO8601InstantJsonDeserializer())
    );

    record TestData(Instant value) {
    }

    String json = """
        { "value":"2013-09-27T11:19:29Z"}
        """;


    TestData actual = mapper.readValue(json, TestData.class);

    assertThat(actual.value()).isEqualTo("2013-09-27T11:19:29Z");

  }
}
