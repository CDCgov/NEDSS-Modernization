package gov.cdc.nbs.patient.demographics.administrative;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AdministrativeSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<Administrative> activeAdministrative() {
    return new Active<>();
  }
}
