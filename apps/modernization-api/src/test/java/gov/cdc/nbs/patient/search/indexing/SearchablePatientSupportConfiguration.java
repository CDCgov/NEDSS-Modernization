package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.search.SearchablePatient;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SearchablePatientSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<SearchablePatient> activeSearchablePatient() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<SearchablePatient> availableSearchablePatient() {
    return new Available<>();
  }
}
