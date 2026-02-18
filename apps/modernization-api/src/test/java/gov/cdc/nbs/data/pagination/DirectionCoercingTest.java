package gov.cdc.nbs.data.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.BooleanValue;
import graphql.language.StringValue;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

class DirectionCoercingTest {

  @Test
  void should_serialize_Direction_as_String() {
    DirectionCoercing coercing = new DirectionCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    String actual = coercing.serialize(Sort.Direction.ASC, context, locale);

    assertThat(actual).isEqualTo("ASC");
  }

  @Test
  void should_only_serialize_Direction() {
    DirectionCoercing coercing = new DirectionCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    String actual = coercing.serialize(new Object(), context, locale);

    assertThat(actual).isNull();
  }

  @Test
  void should_parse_string_as_direction() {

    DirectionCoercing coercing = new DirectionCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    Sort.Direction actual = coercing.parseValue("desc", context, locale);

    assertThat(actual).isEqualTo(Sort.Direction.DESC);
  }

  @Test
  void should_only_parse_string_as_direction() {

    DirectionCoercing coercing = new DirectionCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();

    Sort.Direction actual = coercing.parseValue(new Object(), context, locale);

    assertThat(actual).isNull();
  }

  @Test
  void should_parse_StringValue_literal_as_direction() {

    DirectionCoercing coercing = new DirectionCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();
    CoercedVariables variables = CoercedVariables.emptyVariables();

    Sort.Direction actual =
        coercing.parseLiteral(new StringValue("asc"), variables, context, locale);

    assertThat(actual).isEqualTo(Sort.Direction.ASC);
  }

  @Test
  void should_only_parse_StringValue_literal_as_direction() {

    DirectionCoercing coercing = new DirectionCoercing();

    GraphQLContext context = GraphQLContext.getDefault();
    Locale locale = Locale.getDefault();
    CoercedVariables variables = CoercedVariables.emptyVariables();

    Sort.Direction actual =
        coercing.parseLiteral(new BooleanValue(true), variables, context, locale);

    assertThat(actual).isNull();
  }
}
