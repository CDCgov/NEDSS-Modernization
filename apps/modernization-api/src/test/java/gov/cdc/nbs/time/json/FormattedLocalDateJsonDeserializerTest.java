package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FormattedLocalDateJsonDeserializerTest {
  private ObjectMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ObjectMapper();
  }

  @ParameterizedTest
  @MethodSource("dates")
  void should_deserialize_from_various_date_formats(final String in, final String expected) throws IOException {

    String json = "\"%s\"".formatted(in);
    JsonParser parser = new JsonFactory().createParser(json);
    DeserializationContext context = mapper.getDeserializationContext();

    FormattedLocalDateJsonDeserializer deserializer = new FormattedLocalDateJsonDeserializer();

    //  increment the parser to the first token
    parser.nextToken();

    LocalDate actual = deserializer.deserialize(parser, context);

    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> dates() {
    return Stream.of(
        Arguments.arguments(
            "2023-11-14", "2023-11-14",
            "11/14/2023", "2023-11-14"
        )
    );
  }

  @Test
  void should_deserialize_null_to_null() throws IOException {

    String json = "";
    JsonParser parser = new JsonFactory().createParser(json);
    DeserializationContext context = mapper.getDeserializationContext();

    FormattedLocalDateJsonDeserializer deserializer = new FormattedLocalDateJsonDeserializer();

    //  increment the parser to the first token
    parser.nextToken();

    LocalDate actual = deserializer.deserialize(parser, context);

    assertThat(actual).isNull();

  }
}
