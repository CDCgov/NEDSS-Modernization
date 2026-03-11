package gov.cdc.nbs.patient.demographics.race;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RaceDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Available<RaceDemographic> availableRaceDemographic() {
    return new Available<>();
  }
}
