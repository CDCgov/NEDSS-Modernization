package gov.cdc.nbs.search;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LocalDateWithTimeJsonDeserializerTest {

  private ObjectMapper mapper;

  @BeforeEach
  void setup() {
    mapper = new ObjectMapper();
  }

  @ParameterizedTest
  @MethodSource("dates")
  void should_deserialize_from_various_date_formats(final String in, final String expected)
      throws IOException {

    String json = "\"%s\"".formatted(in);
    JsonParser parser = new JsonFactory().createParser(json);
    DeserializationContext context = mapper.getDeserializationContext();

    LocalDateWithTimeJsonDeserializer deserializer = new LocalDateWithTimeJsonDeserializer();

    //  increment the parser to the first token
    parser.nextToken();

    LocalDate actual = deserializer.deserialize(parser, context);

    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> dates() {
    return Stream.of(
        Arguments.arguments(
            "2023-11-14 00:00:00", "2023-11-14",
            "2023-11-14 00:00:00.0", "2023-11-14",
            "2024-02-13 18:40:39.94", "2024-02-13",
            "2023-11-14 13:16:29.847", "2023-11-14",
            "2024-01-19T13:21:24", "2024-01-19",
            "2024-01-19T13:16:50.157", "2024-01-19"));
  }
}
