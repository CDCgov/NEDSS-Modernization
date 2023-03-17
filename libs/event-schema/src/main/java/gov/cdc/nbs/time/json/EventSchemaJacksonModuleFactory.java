package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;

public class EventSchemaJacksonModuleFactory {

  public static Module create() {
    return new JavaTimeModule()
        .addSerializer(Instant.class, new ISO8601InstantJsonSerializer())
        .addDeserializer(Instant.class, new ISO8601InstantJsonDeserializer());
  }

  private EventSchemaJacksonModuleFactory() {
  }
}
