package gov.cdc.nbs.entity.enums.converter;

import gov.cdc.nbs.exception.ConversionException;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class ElasticsearchInstantValueConverter implements PropertyValueConverter {

  /**
   * Attempts to convert an Instant to a String
   */
  @Override
  public Object write(Object value) {
    if (value instanceof Instant instant) {
      return FlexibleInstantConverter.toString(instant);
    } else {
      return value;
    }
  }

  /**
   * Attempts to convert a String to an Instant
   */
  @Override
  public Object read(Object value) {
    if (value instanceof String s) {
      if (s.startsWith("StringValue{value='")) {
        s = s.substring("StringValue{value='".length(), s.length() - 2);
      }
      try {
        return FlexibleInstantConverter.fromString(s);
      } catch (DateTimeParseException e) {
        throw new ConversionException("Failed to convert String to Instant: " + s);
      }

    } else {
      return value;
    }
  }

}
