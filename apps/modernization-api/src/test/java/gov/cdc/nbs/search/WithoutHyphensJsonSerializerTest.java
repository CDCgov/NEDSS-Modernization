package gov.cdc.nbs.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WithoutHyphensJsonSerializerTest {

  static Stream<Arguments> values() {
    return Stream.of(
        arguments("smith-jones", "\"smith jones\""),
        arguments("smith jones", "\"smith jones\""),
        arguments(null, "null"));
  }

  @ParameterizedTest
  @MethodSource("values")
  void should_retain_spaces_and_replace_hyphens_with_spaces(final String in, final String expected)
      throws IOException {

    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

    new WithoutHyphensJsonSerializer().serialize(in, jsonGenerator, serializerProvider);

    jsonGenerator.flush();

    assertThat(jsonWriter.toString()).contains(expected);
  }
}
