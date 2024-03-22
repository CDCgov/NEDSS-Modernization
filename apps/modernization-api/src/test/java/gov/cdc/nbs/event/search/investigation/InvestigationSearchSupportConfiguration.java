package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InvestigationSearchSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<InvestigationFilter> activeInvestigationFilter() {
    return new Active<>(InvestigationFilter::new);
  }

  @Bean
  @ScenarioScope
  Active<SearchableInvestigation> activeSearchableInvestigation() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<SearchableInvestigation> availableSearchableInvestigation() {
    return new Available<>();
  }
}
