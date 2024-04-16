package gov.cdc.nbs.gateway.home;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
class HomeServiceProvider {

  @Bean
  HomeService homeService(
      @Value("${nbs.gateway.home.uri}") final String host,
      @Value("${nbs.gateway.home.base}") final String base
  ) throws URISyntaxException {
    URI uri = new URI(host);

    return new HomeService(uri, base);
  }

}
