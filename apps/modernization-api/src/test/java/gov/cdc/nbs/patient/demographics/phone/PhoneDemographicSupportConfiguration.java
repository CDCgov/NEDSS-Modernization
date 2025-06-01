package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PhoneDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<PhoneDemographic> activePhoneDemographic() {
    return new Active<>();
  }

}
