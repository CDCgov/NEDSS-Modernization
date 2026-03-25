package gov.cdc.nbs.time.graphql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.NullValue;
import graphql.language.StringValue;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class DateCoercingTest {

  @Test
  void should_serialize_LocalDate_as_ISO_8601_formatted_String() {
    DateCoercing coercing = new DateCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    String actual = coercing.serialize(LocalDate.of(2013, Month.SEPTEMBER, 27), context, locale);

    assertThat(actual).isEqualTo("2013-09-27");
  }

  @Test
  void should_throw_error_when_serializing_unknown_type() {
    DateCoercing coercing = new DateCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    assertThatThrownBy(() -> coercing.serialize(null, context, locale))
        .isInstanceOf(CoercingSerializeException.class)
        .hasMessage("Expected a LocalDate object.");
  }

  @Test
  void should_parse_ISO_8601_formatted_value_as_LocalDate() {

    DateCoercing coercing = new DateCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    LocalDate actual = coercing.parseValue("2013-09-27", context, locale);

    assertThat(actual).isEqualTo("2013-09-27");
  }

  @Test
  void should_parse_formatted_value_as_LocalDate() {
    DateCoercing coercing = new DateCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    LocalDate actual = coercing.parseValue("9/27/2013", context, locale);

    assertThat(actual).isEqualTo("2013-09-27");
  }

  @Test
  void should_throw_error_when_parsing_non_LocalDate_formatted_String_value() {
    DateCoercing coercing = new DateCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    assertThatThrownBy(() -> coercing.parseValue("not a date", context, locale))
        .isInstanceOf(CoercingParseValueException.class)
        .hasMessageContaining("Not a valid date: 'not a date'");
  }

  @Test
  void should_throw_error_when_parsing_non_String_value() {
    DateCoercing coercing = new DateCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    assertThatThrownBy(() -> coercing.parseValue(839, context, locale))
        .isInstanceOf(CoercingParseValueException.class)
        .hasMessageContaining("Expected a String");
  }

  @Test
  void should_parse_ISO_8601_formatted_StringValue_literal_as_LocalDate() {

    DateCoercing coercing = new DateCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();
    CoercedVariables variables = CoercedVariables.emptyVariables();

    LocalDate actual =
        coercing.parseLiteral(new StringValue("2013-09-27"), variables, context, locale);

    assertThat(actual).isEqualTo("2013-09-27");
  }

  @Test
  void should_throw_error_when_parsing_non_StringValue_literal() {

    DateCoercing coercing = new DateCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();
    CoercedVariables variables = CoercedVariables.emptyVariables();

    NullValue value = NullValue.of();

    assertThatThrownBy(() -> coercing.parseLiteral(value, variables, context, locale))
        .isInstanceOf(CoercingParseLiteralException.class)
        .hasMessageContaining("Expected a StringValue.");
  }
}
