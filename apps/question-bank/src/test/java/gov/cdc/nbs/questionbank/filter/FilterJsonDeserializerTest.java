package gov.cdc.nbs.questionbank.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class FilterJsonDeserializerTest {


  private static final ObjectMapper MAPPER = new ObjectMapper();

  @ParameterizedTest
  @MethodSource("valueFilters")
  void should_deserialize_single_value_filter(
      final String operator,
      final ValueFilter.Operator expected
  ) throws IOException {

    String json = """
        {
          "property":"property-name",
          "operator": "%s",
          "value" : "value-value"
        }
        """;

    StringReader reader = new StringReader(String.format(json,operator));

    JsonParser parser = MAPPER.getFactory().createParser(reader);

    FilterJsonDeserializer deserializer = new FilterJsonDeserializer();

    Filter actual = deserializer.deserialize(parser, MAPPER.getDeserializationContext());

    assertThat(actual).asInstanceOf(type(SingleValueFilter.class))
        .returns("property-name", SingleValueFilter::property)
        .returns(expected, ValueFilter::operator)
        .returns("value-value", SingleValueFilter::value)
    ;

  }

  @ParameterizedTest
  @MethodSource("valueFilters")
  void should_deserialize_multi_value_filter(
      final String operator,
      final ValueFilter.Operator expected
  ) throws IOException {

    String json = """
        {
          "property":"property-name",
          "operator": "%s",
          "values" : ["one", "two", "three"]
        }
        """;

    StringReader reader = new StringReader(String.format(json,operator));

    JsonParser parser = MAPPER.getFactory().createParser(reader);

    FilterJsonDeserializer deserializer = new FilterJsonDeserializer();

    Filter actual = deserializer.deserialize(parser, MAPPER.getDeserializationContext());

    assertThat(actual).asInstanceOf(type(MultiValueFilter.class))
        .returns("property-name", MultiValueFilter::property)
        .returns(expected, ValueFilter::operator)
        .extracting(MultiValueFilter::values, list(String.class))
        .contains("one", "two", "three");

  }

  static Stream<Arguments> valueFilters() {
    return Stream.of(
      arguments("equals", ValueFilter.Operator.EQUALS),
        arguments("not equal to", ValueFilter.Operator.NOT_EQUAL_TO),
        arguments("not-equal-to", ValueFilter.Operator.NOT_EQUAL_TO),
        arguments("not_equal_to", ValueFilter.Operator.NOT_EQUAL_TO),
        arguments("starts with", ValueFilter.Operator.STARTS_WITH),
        arguments("starts-with", ValueFilter.Operator.STARTS_WITH),
        arguments("starts_with", ValueFilter.Operator.STARTS_WITH),
        arguments("contains", ValueFilter.Operator.CONTAINS)
    );
  }
}
