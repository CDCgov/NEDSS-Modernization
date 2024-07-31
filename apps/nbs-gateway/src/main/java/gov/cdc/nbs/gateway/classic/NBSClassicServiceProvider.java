package gov.cdc.nbs.gateway.classic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
class NBSClassicServiceProvider {

  @Bean
  NBSClassicService nbsClassicService(
      @Value("${nbs.gateway.classic}") final String classic) throws URISyntaxException {
    URI uri = new URI(classic);
    return new NBSClassicService(uri);
  }

}
