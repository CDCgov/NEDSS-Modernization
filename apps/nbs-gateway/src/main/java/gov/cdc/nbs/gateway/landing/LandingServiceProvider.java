package gov.cdc.nbs.gateway.landing;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LandingServiceProvider {

  @Bean
  LandingService landingService(
      @Value("${nbs.gateway.landing.uri:no://op}") final String host,
      @Value("${nbs.gateway.landing.base:/nbs/login}") final String base)
      throws URISyntaxException {
    URI uri = new URI(host);

    return new LandingService(uri, base);
  }
}
