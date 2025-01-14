package gov.cdc.nbs.data.pagination;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FlexibleDirectionJsonDeserializerTest {

  record TestData(Sort.Direction value) {
  }

  @ParameterizedTest
  @MethodSource("knownDirections")
  void should_deserialize_into_known_values(
      final String in,
      final Sort.Direction expected
  ) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(
        new SimpleModule()
            .addDeserializer(Sort.Direction.class, new FlexibleDirectionJsonDeserializer())
    );

    String json = """
        { "value":"%s"}
        """.formatted(in);

    TestData actual = mapper.readValue(json, TestData.class);

    assertThat(actual.value()).isEqualTo(expected);
  }

  static Stream<Arguments> knownDirections() {
    return Stream.of(
        Arguments.arguments("desc", Sort.Direction.DESC),
        Arguments.arguments("DESC", Sort.Direction.DESC),
        Arguments.arguments("asc", Sort.Direction.ASC),
        Arguments.arguments("ASC", Sort.Direction.ASC)
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"unknown", "", "other", "de", "as"})
  void should_return_null_for_unknown_values(final String in) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(
        new SimpleModule()
            .addDeserializer(Sort.Direction.class, new FlexibleDirectionJsonDeserializer())
    );

    String json = """
        { "value":"%s"}
        """.formatted(in);

    TestData actual = mapper.readValue(json, TestData.class);

    assertThat(actual.value()).isNull();
  }
}
