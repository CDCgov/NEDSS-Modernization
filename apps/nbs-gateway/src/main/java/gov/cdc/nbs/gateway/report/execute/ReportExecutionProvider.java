package gov.cdc.nbs.gateway.report.execute;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReportExecutionProvider {

  @Bean
  ReportExecutionService reportExecutionRoute(
      @Value("${nbs.gateway.modernization.protocol:${nbs.gateway.defaults.protocol}}")
          final String protocol,
      @Value("${nbs.gateway.modernization.service}") final String service)
      throws URISyntaxException {
    URI uri = new URI(protocol + "://" + service);

    return new ReportExecutionService(uri);
  }
}
