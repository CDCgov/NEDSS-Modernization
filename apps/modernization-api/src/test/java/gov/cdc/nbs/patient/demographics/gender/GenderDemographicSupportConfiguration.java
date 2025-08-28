package gov.cdc.nbs.patient.demographics.gender;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GenderDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<GenderDemographic> activeGenderDemographic() {
    return new Active<>();
  }
}
