package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AddressDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Available<AddressDemographic> availableAddressDemographic() {
    return new Available<>();
  }

}
