package gov.cdc.nbs.data.pagination;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import jakarta.annotation.Nonnull;
import java.util.Locale;
import org.springframework.data.domain.Sort;

class DirectionCoercing implements Coercing<Sort.Direction, String> {

  @Override
  public String serialize(
      @Nonnull final Object dataFetcherResult,
      @Nonnull final GraphQLContext graphQLContext,
      @Nonnull final Locale locale)
      throws CoercingSerializeException {
    return (dataFetcherResult instanceof Sort.Direction direction) ? direction.name() : null;
  }

  @Override
  public Sort.Direction parseValue(
      @Nonnull final Object input,
      @Nonnull final GraphQLContext graphQLContext,
      @Nonnull final Locale locale)
      throws CoercingParseValueException {
    return (input instanceof String value) ? FlexibleDirectionConverter.from(value) : null;
  }

  @Override
  public Sort.Direction parseLiteral(
      @Nonnull final Value<?> input,
      @Nonnull final CoercedVariables variables,
      @Nonnull final GraphQLContext graphQLContext,
      @Nonnull final Locale locale)
      throws CoercingParseLiteralException {
    return (input instanceof StringValue value)
        ? parseValue(value.getValue(), graphQLContext, locale)
        : null;
  }
}
