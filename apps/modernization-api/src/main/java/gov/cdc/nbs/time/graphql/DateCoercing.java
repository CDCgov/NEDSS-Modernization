package gov.cdc.nbs.time.graphql;

import gov.cdc.nbs.time.FlexibleLocalDateConverter;
import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import jakarta.annotation.Nonnull;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateCoercing implements Coercing<LocalDate, String> {

  @Nonnull
  @Override
  public String serialize(
      final @Nonnull Object dataFetcherResult,
      final @Nonnull GraphQLContext context,
      final @Nonnull Locale locale)
      throws CoercingSerializeException {
    if (dataFetcherResult instanceof LocalDate date) {
      return FlexibleLocalDateConverter.toString(date);
    } else {
      throw new CoercingSerializeException("Expected a LocalDate object.");
    }
  }

  @Nonnull
  @Override
  public LocalDate parseValue(
      final @Nonnull Object input,
      final @Nonnull GraphQLContext context,
      final @Nonnull Locale locale)
      throws CoercingParseValueException {
    try {
      if (input instanceof String value) {
        return FlexibleLocalDateConverter.fromString(value);
      } else {
        throw new CoercingParseValueException("Expected a String");
      }
    } catch (DateTimeParseException e) {
      throw new CoercingParseValueException("Not a valid date: '%s'.".formatted(input), e);
    }
  }

  //  Suppressing java:S2637 ("@NonNull" values should not be set to null) because there is a known
  //  issue regarding that Sonar rule throwing false positives in certain situations, which seems
  //  to be the case here.  The `value.getValue() != null` check prior to invoking `parseValue`
  //  guarantees the first parameter will never be null, and yet the issue is still triggered.
  //
  //  https://sonarsource.atlassian.net/browse/JAVASE-91
  @SuppressWarnings("java:S2637")
  @Nonnull
  @Override
  public LocalDate parseLiteral(
      @Nonnull final Value<?> input,
      @Nonnull final CoercedVariables variables,
      @Nonnull final GraphQLContext context,
      @Nonnull final Locale locale)
      throws CoercingParseLiteralException {
    if (input instanceof StringValue value && value.getValue() != null) {
      return parseValue(value.getValue(), context, locale);
    } else {
      throw new CoercingParseLiteralException("Expected a StringValue.");
    }
  }
}
