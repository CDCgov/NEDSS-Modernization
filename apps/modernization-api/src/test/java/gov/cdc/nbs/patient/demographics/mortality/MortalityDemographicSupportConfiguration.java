package gov.cdc.nbs.patient.demographics.mortality;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MortalityDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<MortalityDemographic> activeMortalityDemographic() {
    return new Active<>();
  }
}
