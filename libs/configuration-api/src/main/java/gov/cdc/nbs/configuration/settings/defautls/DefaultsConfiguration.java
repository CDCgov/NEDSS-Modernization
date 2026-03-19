package gov.cdc.nbs.configuration.settings.defautls;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class DefaultsConfiguration {

  @Bean
  @Scope("prototype")
  Defaults defaults(
      @Value("${nbs.ui.settings.defaults.sizing:medium}") final String sizing,
      @Value("${nbs.ui.settings.defaults.country:840}") final String country) {
    return new Defaults(sizing, country);
  }
}
