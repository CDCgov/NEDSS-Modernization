package gov.cdc.nbs.time.graphql;

import graphql.language.NullValue;
import graphql.language.StringValue;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingSerializeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateCoercingTest {

  @Test
  void should_serialize_LocalDate_as_ISO_8601_formatted_String() {
    DateCoercing coercing = new DateCoercing();

    String actual = coercing.serialize(LocalDate.of(2013, Month.SEPTEMBER, 27));

    assertThat(actual).isEqualTo("2013-09-27");
  }

  @Test
  void should_throw_error_when_serializing_unknown_type() {
    DateCoercing coercing = new DateCoercing();

    assertThatThrownBy(() -> coercing.serialize(null))
        .isInstanceOf(CoercingSerializeException.class)
        .hasMessage("Expected a LocalDate object.");

  }

  @Test
  void should_parse_ISO_8601_formatted_value_as_LocalDate() {

    DateCoercing coercing = new DateCoercing();

    LocalDate actual = coercing.parseValue("2013-09-27");

    assertThat(actual).isEqualTo("2013-09-27");

  }

  @Test
  void should_parse_formatted_value_as_LocalDate() {
    DateCoercing coercing = new DateCoercing();

    LocalDate actual = coercing.parseValue("9/27/2013");

    assertThat(actual).isEqualTo("2013-09-27");
  }

  @Test
  void should_parse_ISO_8601_formatted_StringValue_literal_as_LocalDate() {

    DateCoercing coercing = new DateCoercing();

    LocalDate actual = coercing.parseLiteral(new StringValue("2013-09-27"));

    assertThat(actual).isEqualTo("2013-09-27");

  }

  @Test
  void should_throw_error_when_parsing_non_StringValue_literal() {

    DateCoercing coercing = new DateCoercing();

    NullValue value = NullValue.of();

    assertThatThrownBy(() -> coercing.parseLiteral(value))
        .isInstanceOf(CoercingParseLiteralException.class)
        .hasMessage("Expected a StringValue.");


  }

}
