package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.search.SimpleIndex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SearchableLabReportConfiguration {

  @Bean
  SimpleIndex labReportIndex(
      @Value("${nbs.search.lab-report.index.name}") final String index,
      @Value("${nbs.search.lab-report.index.mapping}") final String location) {
    return new SimpleIndex(index, location);
  }
}
