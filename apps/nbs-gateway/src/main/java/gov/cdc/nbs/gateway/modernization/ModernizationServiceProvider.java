package gov.cdc.nbs.gateway.modernization;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ModernizationServiceProvider {

  @Bean
  ModernizationService modernizationService(
      @Value("${nbs.gateway.modernization.uri}") final String host) throws URISyntaxException {
    URI uri = new URI(host);

    return new ModernizationService(uri);
  }
}
