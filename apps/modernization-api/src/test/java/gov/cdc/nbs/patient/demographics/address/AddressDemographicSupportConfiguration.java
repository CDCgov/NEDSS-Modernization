package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AddressDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<AddressDemographic> activeAddressDemographic() {
    return new Active<>();
  }

}
