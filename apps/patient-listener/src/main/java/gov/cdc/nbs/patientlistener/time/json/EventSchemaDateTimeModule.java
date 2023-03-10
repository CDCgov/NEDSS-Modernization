package gov.cdc.nbs.patientlistener.time.json;

import com.fasterxml.jackson.databind.Module;
import gov.cdc.nbs.time.json.EventSchemaJacksonModuleFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EventSchemaDateTimeModule {

  @Bean
  Module eventSchemaDateTimeModule() {
    return EventSchemaJacksonModuleFactory.create();
  }
}
