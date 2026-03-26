package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PhoneDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Available<PhoneDemographic> availablePhoneDemographic() {
    return new Available<>();
  }
}
