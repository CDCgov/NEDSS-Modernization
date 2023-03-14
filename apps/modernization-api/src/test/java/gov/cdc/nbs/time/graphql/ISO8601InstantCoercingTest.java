package gov.cdc.nbs.time.graphql;

import graphql.language.NullValue;
import graphql.language.StringValue;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ISO8601InstantCoercingTest {

  @Test
  void should_serialize_Instant_as_ISO_8601_formatted_String() {
    ISO8601InstantCoercing coercing = new ISO8601InstantCoercing();

    String actual = coercing.serialize(Instant.parse("2013-09-27T11:19:29Z"));

    assertThat(actual).isEqualTo("2013-09-27T11:19:29Z");
  }

  @Test
  void should_throw_error_when_serializing_unknown_type() {
    ISO8601InstantCoercing coercing = new ISO8601InstantCoercing();

    assertThatThrownBy(() -> coercing.serialize(null))
        .isInstanceOf(CoercingSerializeException.class)
        .hasMessage("Expected an Instant object.");

  }

  @Test
  void should_parse_ISO_8601_formatted_value_as_Instant() {

    ISO8601InstantCoercing coercing = new ISO8601InstantCoercing();

    Instant actual = coercing.parseValue("2013-09-27T11:19:29Z");

    assertThat(actual).isEqualTo("2013-09-27T11:19:29Z");

  }

  @Test
  void should_throw_error_when_parsing_non_ISO8601_format() {
    ISO8601InstantCoercing coercing = new ISO8601InstantCoercing();

    assertThatThrownBy(() -> coercing.parseValue("05/11/2011"))
        .isInstanceOf(CoercingParseValueException.class)
        .hasMessage("Not a valid datetime: '05/11/2011'.");

  }

  @Test
  void should_parse_ISO_8601_formatted_StringValue_literal_as_Instant() {

    ISO8601InstantCoercing coercing = new ISO8601InstantCoercing();

    Instant actual = coercing.parseLiteral(new StringValue("2013-09-27T11:19:29Z"));

    assertThat(actual).isEqualTo("2013-09-27T11:19:29Z");

  }

  @Test
  void should_throw_error_when_parsing_non_StringValue_literal() {

    ISO8601InstantCoercing coercing = new ISO8601InstantCoercing();

    NullValue value = NullValue.of();

    assertThatThrownBy(() -> coercing.parseLiteral(value))
        .isInstanceOf(CoercingParseLiteralException.class)
        .hasMessage("Expected a StringValue.");


  }
}
