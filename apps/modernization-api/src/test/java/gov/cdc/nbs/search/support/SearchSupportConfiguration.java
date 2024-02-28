package gov.cdc.nbs.search.support;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SearchSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<SortCriteria> activePatientSortCriteria() {
    return new Active<>();
  }

}
