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

class WithoutSpecialCharactersJsonSerializerTest {

  static Stream<Arguments> values() {
    return Stream.of(
        arguments("c!l@e#a$n%e^d v&a*l(u)e-=", "\"cleanedvalue\""),
        arguments("2!3@5#7$11%13^17 19&23*29(31)37-41=43", "\"235711131719232931374143\""),
        arguments("c!257l@e#a$n%e^d v&a*613l(u)e-=", "\"c257leanedva613lue\""),
        arguments(null, "null"));
  }

  @ParameterizedTest
  @MethodSource("values")
  void should_serialize_string_to_with_only_letters_and_digits(
      final String in, final String expected) throws IOException {

    Writer jsonWriter = new StringWriter();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

    new WithoutSpecialCharactersJsonSerializer().serialize(in, jsonGenerator, serializerProvider);

    jsonGenerator.flush();

    assertThat(jsonWriter.toString()).contains(expected);
  }
}
