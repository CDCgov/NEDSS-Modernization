package gov.cdc.nbs.time.json;

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

class FormattedLocalDateJsonSerializerTest {

  @Test
  void should_serialize_LocalDate_to_the_design_system_date_format() throws IOException {

    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

    new FormattedLocalDateJsonSerializer()
        .serialize(LocalDate.of(2019, Month.MARCH, 17), jsonGenerator, serializerProvider);

    jsonGenerator.flush();

    assertThat(jsonWriter.toString()).contains("03/17/2019");
  }

  @Test
  void should_serialize_null_LocalDate_to_null() throws IOException {

    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

    new FormattedLocalDateJsonSerializer().serialize(null, jsonGenerator, serializerProvider);

    jsonGenerator.flush();

    assertThat(jsonWriter.toString()).contains("null");
  }
}
