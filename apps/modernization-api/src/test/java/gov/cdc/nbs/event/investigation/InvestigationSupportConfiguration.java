package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InvestigationSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<InvestigationIdentifier> activeInvestigation() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<InvestigationIdentifier> availableInvestigation() {
    return new Available<>();
  }

  @Bean
  @ScenarioScope
  Active<AbcCaseIdentifier> activeAbcCaseId() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Active<StateCaseIdentifier> activeStateCaseId() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Active<CityCountyCaseIdentifier> activeCityCountyCaseId() {
    return new Active<>();
  }

}
