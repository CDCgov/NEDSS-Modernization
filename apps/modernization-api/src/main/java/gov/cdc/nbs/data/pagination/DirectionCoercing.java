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

  //  Suppressing java:2637 ("@NonNull" values should not be set to null) because there is a known
  //  issue regarding that Sonar rule throwing false positives in certain situations, which seems
  //  to be the case here.  The `value.getValue() != null` check prior to invoking `parseValue`
  //  guarantees the first parameter will never be null, and yet the issue is still triggered.
  //
  //  https://sonarsource.atlassian.net/browse/JAVASE-91
  @SuppressWarnings("java:S2637")
  @Override
  public Sort.Direction parseLiteral(
      @Nonnull final Value<?> input,
      @Nonnull final CoercedVariables variables,
      @Nonnull final GraphQLContext graphQLContext,
      @Nonnull final Locale locale)
      throws CoercingParseLiteralException {
    return (input instanceof StringValue value && value.getValue() != null)
        ? parseValue(value.getValue(), graphQLContext, locale)
        : null;
  }
}
