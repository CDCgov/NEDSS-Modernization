package gov.cdc.nbs.gateway.logo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogoConfiguration {

  @Bean
  LogoSettings logoSettings(
      @Value("${nbs.gateway.logo.path:/images/nedssLogo.jpg}") final String path,
      @Value("${nbs.gateway.logo.resource:/nedssLogo.jpeg}") final String resource,
      @Value("${nbs.gateway.logo.file:}") final String file
  ) {
    return new LogoSettings(path, resource, file);
  }

}
