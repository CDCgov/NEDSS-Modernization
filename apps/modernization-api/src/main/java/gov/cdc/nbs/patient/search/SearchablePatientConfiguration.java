package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.search.SimpleIndex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SearchablePatientConfiguration {

  @Bean
  SimpleIndex patientIndex(
      @Value("${nbs.search.patient.index.name}") final String index,
      @Value("${nbs.search.patient.index.mapping}") final String location) {

    return new SimpleIndex(index, location);
  }
}
