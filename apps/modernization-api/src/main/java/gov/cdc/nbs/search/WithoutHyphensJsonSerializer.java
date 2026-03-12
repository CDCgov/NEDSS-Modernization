package gov.cdc.nbs.search;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class WithoutHyphensJsonSerializer extends JsonSerializer<String> {

  @Override
  public void serialize(
      final String value,
      final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider)
      throws IOException {
    String adjusted = AdjustStrings.withoutHyphens(value);

    if (adjusted == null) {
      jsonGenerator.writeNull();
    } else {
      jsonGenerator.writeString(adjusted);
    }
  }
}
