package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class InvestigationSupportConfiguration {

  @Bean
  Active<InvestigationIdentifier> activeInvestigation() {
    return new Active<>();
  }

  @Bean
  Available<InvestigationIdentifier> availableInvestigation() {
    return new Available<>();
  }

  @Bean
  Active<AbcCaseIdentifier> activeAbcCaseId() {
    return new Active<>();
  }

  @Bean
  Active<StateCaseIdentifier> activeStateCaseId() {
    return new Active<>();
  }

  @Bean
  Active<CityCountyCaseIdentifier> activeCityCountyCaseId() {
    return new Active<>();
  }

}
