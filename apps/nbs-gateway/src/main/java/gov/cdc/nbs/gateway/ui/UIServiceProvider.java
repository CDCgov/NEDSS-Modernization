package gov.cdc.nbs.gateway.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
class UIServiceProvider {

  @Bean
  UIService uiService(
      @Value("${nbs.gateway.ui.uri}") final String host
  ) throws URISyntaxException {
    URI uri = new URI(host);

    return new UIService(uri);
  }

}
