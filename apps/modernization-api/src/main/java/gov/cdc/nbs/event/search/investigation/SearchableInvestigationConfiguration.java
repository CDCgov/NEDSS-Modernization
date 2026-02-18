package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.search.SimpleIndex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SearchableInvestigationConfiguration {

  @Bean
  SimpleIndex investigationIndex(
      @Value("${nbs.search.investigation.index.name}") final String index,
      @Value("${nbs.search.investigation.index.mapping}") final String location) {
    return new SimpleIndex(index, location);
  }
}
