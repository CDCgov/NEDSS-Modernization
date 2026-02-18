package gov.cdc.nbs.search;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.Test;

class LocalDateWithTimeJsonSerializerTest {

  @Test
  void should_serialize_LocalDate_to_ISO_8601_at_start_of_day() throws IOException {

    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

    new LocalDateWithTimeJsonSerializer()
        .serialize(LocalDate.of(2019, Month.MARCH, 17), jsonGenerator, serializerProvider);

    jsonGenerator.flush();

    assertThat(jsonWriter.toString()).contains("2019-03-17T00:00:00");
  }

  @Test
  void should_serialize_null_LocalDate_to_null() throws IOException {

    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

    new LocalDateWithTimeJsonSerializer().serialize(null, jsonGenerator, serializerProvider);

    jsonGenerator.flush();

    assertThat(jsonWriter.toString()).contains("null");
  }
}
