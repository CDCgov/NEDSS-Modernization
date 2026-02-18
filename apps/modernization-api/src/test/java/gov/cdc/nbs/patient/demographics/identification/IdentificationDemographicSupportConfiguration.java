package gov.cdc.nbs.patient.demographics.identification;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class IdentificationDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Available<IdentificationDemographic> availableIdentificationDemographic() {
    return new Available<>();
  }
}
