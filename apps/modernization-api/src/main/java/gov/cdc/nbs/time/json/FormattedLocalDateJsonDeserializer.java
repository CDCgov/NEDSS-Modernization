package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class FormattedLocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {

  private static final DateTimeFormatter READER = new DateTimeFormatterBuilder()
      .appendOptional(DateTimeFormatter.ofPattern("MM/dd/uuuu"))
      .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
      .toFormatter();

  @Override
  public LocalDate deserialize(
      final JsonParser jsonParser,
      final DeserializationContext deserializationContext
  )
      throws IOException {

    String value = jsonParser.getValueAsString();

    return (value == null)
        ? null
        : LocalDate.parse(value, READER);
  }
}
