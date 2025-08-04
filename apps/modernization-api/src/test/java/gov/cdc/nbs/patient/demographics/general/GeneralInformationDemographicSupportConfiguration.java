package gov.cdc.nbs.patient.demographics.general;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GeneralInformationDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<GeneralInformationDemographic> activeGeneralInformationDemographic() {
    return new Active<>();
  }
}
