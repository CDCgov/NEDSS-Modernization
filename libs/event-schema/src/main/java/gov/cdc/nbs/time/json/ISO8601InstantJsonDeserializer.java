package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

/**
 * Deserializes a JSON {@code string}in the ISO-8601 Instant format into an {@code Instant} value.
 */
public class ISO8601InstantJsonDeserializer extends JsonDeserializer<Instant> {
  @Override
  public Instant deserialize(
      final JsonParser parser,
      final DeserializationContext context
  ) throws IOException {
    return Instant.parse(parser.getText());
  }
}
