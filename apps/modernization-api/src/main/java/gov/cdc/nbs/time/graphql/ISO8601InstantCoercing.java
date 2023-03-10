package gov.cdc.nbs.time.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses and serializes {@code Instant} values using the ISO-8601 standard.
 */
public class ISO8601InstantCoercing implements Coercing<Instant, String> {

  @Override
  @Nonnull
  public String serialize(final @Nonnull Object dataFetcherResult) {
    if (dataFetcherResult instanceof Instant instant) {
      return DateTimeFormatter.ISO_INSTANT.format(instant);
    } else {
      throw new CoercingSerializeException("Expected an Instant object.");
    }
  }

  @Override
  @Nonnull
  public Instant parseValue(final @Nonnull Object input) {
    try {
      if (input instanceof String value) {
        return Instant.parse(value);
      } else {
        throw new CoercingParseValueException("Expected a String");
      }
    } catch (DateTimeParseException e) {
      throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e);
    }
  }

  @Override
  @Nonnull
  public Instant parseLiteral(final @Nonnull Object input) {
    if (input instanceof StringValue value) {
      return parseValue(value.getValue());
    } else {
      throw new CoercingParseLiteralException("Expected a StringValue.");
    }
  }
}
