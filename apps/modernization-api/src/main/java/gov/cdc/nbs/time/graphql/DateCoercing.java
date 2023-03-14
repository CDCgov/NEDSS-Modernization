package gov.cdc.nbs.time.graphql;

import gov.cdc.nbs.time.FlexibleLocalDateConverter;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateCoercing implements Coercing<LocalDate, String> {

  @Nonnull
  @Override
  public String serialize(final @Nonnull Object dataFetcherResult) throws CoercingSerializeException {
    if (dataFetcherResult instanceof LocalDate date) {
      return FlexibleLocalDateConverter.toString(date);
    } else {
      throw new CoercingSerializeException("Expected a LocalDate object.");
    }
  }

  @Nonnull
  @Override
  public LocalDate parseValue(final @Nonnull Object input) throws CoercingParseValueException {
    try {
      if (input instanceof String value) {
        return FlexibleLocalDateConverter.fromString(value);
      } else {
        throw new CoercingParseValueException("Expected a String");
      }
    } catch (DateTimeParseException e) {
      throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e);
    }
  }

  @Nonnull
  @Override
  public LocalDate parseLiteral(final @Nonnull Object input) throws CoercingParseLiteralException {
    if (input instanceof StringValue value) {
      return parseValue(value.getValue());
    } else {
      throw new CoercingParseLiteralException("Expected a StringValue.");
    }
  }
}
