package gov.cdc.nbs.time.graphql;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/** Parses and serializes {@code Instant} values using the ISO-8601 standard. */
public class ISO8601InstantCoercing implements Coercing<Instant, String> {

  @Override
  @Nonnull
  public String serialize(
      final @Nonnull Object dataFetcherResult,
      final @Nonnull GraphQLContext context,
      final @Nonnull Locale locale) {
    if (dataFetcherResult instanceof Instant instant) {
      return DateTimeFormatter.ISO_INSTANT.format(instant);
    } else {
      throw new CoercingSerializeException("Expected an Instant object.");
    }
  }

  @Override
  @Nonnull
  public Instant parseValue(
      final @Nonnull Object input,
      final @Nonnull GraphQLContext context,
      final @Nonnull Locale locale) {
    try {
      if (input instanceof String value) {
        return Instant.parse(value);
      } else {
        throw new CoercingParseValueException("Expected a String");
      }
    } catch (DateTimeParseException e) {
      throw new CoercingParseValueException("Not a valid datetime: '%s'.".formatted(input), e);
    }
  }

  @Override
  @Nonnull
  public Instant parseLiteral(
      @Nonnull final Value<?> input,
      @Nonnull final CoercedVariables variables,
      @Nonnull final GraphQLContext context,
      @Nonnull final Locale locale) {
    if (input instanceof StringValue value) {
      return parseValue(value.getValue(), context, locale);
    } else {
      throw new CoercingParseLiteralException("Expected a StringValue.");
    }
  }
}
