package gov.cdc.nbs.time.json;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NBSJacksonDateTimeModule {

  @Bean
  Module nbsTimeModule() {
    return EventSchemaJacksonModuleFactory.create();
  }
}
