package gov.cdc.nbs.redirect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
class RedirectionConfiguration {

  @Bean
  DefaultRedirectionPath redirectConfiguration(
      @Value("${nbs.redirect.default:/search}") final String defaultPath) {
    return new DefaultRedirectionPath(defaultPath);
  }

}
