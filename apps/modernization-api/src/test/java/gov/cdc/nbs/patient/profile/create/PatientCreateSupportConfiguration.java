package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientCreateSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<NewPatient> activeNewPatient() {
    return new Active<>(NewPatient::new);
  }
}
