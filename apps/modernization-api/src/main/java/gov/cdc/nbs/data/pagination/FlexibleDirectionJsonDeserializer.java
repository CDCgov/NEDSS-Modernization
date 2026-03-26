package gov.cdc.nbs.data.pagination;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.springframework.data.domain.Sort;

/**
 * Deserializes a JSON {@code string} into a {@code Direction}, or null if the direction cannot be
 * resolved.
 */
public class FlexibleDirectionJsonDeserializer extends JsonDeserializer<Sort.Direction> {
  @Override
  public Sort.Direction deserialize(final JsonParser parser, final DeserializationContext context)
      throws IOException {
    return FlexibleDirectionConverter.from(parser.getText());
  }
}
