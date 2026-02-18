package gov.cdc.nbs.search;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A {@link JsonSerializer} for {@link LocalDate} instances that serializes the date in the ISO-8601
 * extended offset date-time format; yyyy-MM-ddTmm:hh:ss. The time portion will be at the start of
 * the day.
 */
public class LocalDateWithTimeJsonSerializer extends JsonSerializer<LocalDate> {

  @Override
  public void serialize(
      final LocalDate value,
      final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider)
      throws IOException {

    if (value == null) {
      jsonGenerator.writeNull();
    } else {
      String formatted = value.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
      jsonGenerator.writeString(formatted);
    }
  }
}
