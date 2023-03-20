package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Serializes an {@code Instant} as a {@code string} value in the ISO-8601 Instant format.
 */
public class ISO8601InstantJsonSerializer extends JsonSerializer<Instant> {

  @Override
  public void serialize(
      final Instant value,
      final JsonGenerator generator,
      final SerializerProvider serializers
  ) throws IOException {
    String serialized = DateTimeFormatter.ISO_INSTANT.format(value);

    generator.writeString(serialized);
  }
}
