package gov.cdc.nbs.questionbank.filter;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FilterJsonModuleConfiguration {

  @Bean
  Module filterJsonModule() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Filter.class, new FilterJsonDeserializer());
    return module;
  }

}
