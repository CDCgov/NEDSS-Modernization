package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A {@link JsonSerializer} for {@link LocalDate} instances that serializes the date in the ISO-8601 extended offset
 * date-time format; yyyy-MM-ddTmm:hh:ss.  The time portion will be at the start of the day.
 */
public class FormattedLocalDateJsonSerializer extends JsonSerializer<LocalDate> {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  @Override
  public void serialize(
      final LocalDate value,
      final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider
  ) throws IOException {

    if (value == null) {
      jsonGenerator.writeNull();
    } else {
      String formatted = value.atStartOfDay().format(formatter);
      jsonGenerator.writeString(formatted);
    }
  }
}
