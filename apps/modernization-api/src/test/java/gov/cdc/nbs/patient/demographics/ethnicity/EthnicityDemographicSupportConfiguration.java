package gov.cdc.nbs.patient.demographics.ethnicity;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EthnicityDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<EthnicityDemographic> activeEthnicityDemographic() {
    return new Active<>();
  }

}
