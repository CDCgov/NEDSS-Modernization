package gov.cdc.nbs.gateway.patient.search;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientSearchProvider {

  @Bean
  PatientSearchService searchRoute(
      @Value("${nbs.gateway.patient.search.protocol:${nbs.gateway.defaults.protocol}}")
          final String protocol,
      @Value("${nbs.gateway.patient.search.service}") final String service)
      throws URISyntaxException {
    URI uri = new URI(protocol + "://" + service);

    return new PatientSearchService(uri);
  }
}
