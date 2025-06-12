package gov.cdc.nbs.patient.demographics.race;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RaceDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<RaceDemographic> activeRaceDemographic() {
    return new Active<>();
  }

}
