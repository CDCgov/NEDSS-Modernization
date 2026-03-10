package gov.cdc.nbs.search;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class LocalDateWithTimeJsonDeserializer extends JsonDeserializer<LocalDate> {

  private static final DateTimeFormatter READER =
      new DateTimeFormatterBuilder()
          .appendOptional(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss[.[SSS][SS][S]]"))
          .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
          .toFormatter();

  @Override
  public LocalDate deserialize(
      final JsonParser jsonParser, final DeserializationContext deserializationContext)
      throws IOException {

    String value = jsonParser.getValueAsString();

    if (value == null) {
      return null;
    } else {
      return LocalDate.parse(value, READER);
    }
  }
}
