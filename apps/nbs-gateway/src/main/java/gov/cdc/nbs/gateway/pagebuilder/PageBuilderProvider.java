package gov.cdc.nbs.gateway.pagebuilder;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageBuilderProvider {

  @Bean
  PageBuilderService pageBuilderManagePagesRoute(
      @Value("${nbs.gateway.pagebuilder.protocol:${nbs.gateway.defaults.protocol}}")
          final String protocol,
      @Value("${nbs.gateway.pagebuilder.service}") final String service,
      @Value("${nbs.gateway.pagebuilder.base:/nbs/page-builder/api/v1/}") final String base)
      throws URISyntaxException {
    URI uri = new URI(protocol + "://" + service);

    return new PageBuilderService(uri, base);
  }
}
