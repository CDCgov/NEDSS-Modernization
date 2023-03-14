package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ISO8601InstantJsonSerializerTest {

  @Test
  void should_serialize_Instant_as_ISO_8601_formatted_string() throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(
        new SimpleModule()
            .addSerializer(Instant.class, new ISO8601InstantJsonSerializer())
    );

    StringWriter writer = new StringWriter();


    mapper.writeValue(writer, Instant.parse("2013-09-27T11:19:29Z"));

    assertThat(writer.toString()).contains("2013-09-27T11:19:29Z");
  }
}
