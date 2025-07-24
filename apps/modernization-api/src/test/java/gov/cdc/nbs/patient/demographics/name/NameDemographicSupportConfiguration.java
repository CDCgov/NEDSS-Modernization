package gov.cdc.nbs.patient.demographics.name;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NameDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<NameDemographic> activeNameDemographic() {
    return new Active<>();
  }

}
