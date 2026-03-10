package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<PatientIdentifier> activePatient() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  TestPatientIdentifier availablePatients() {
    return new TestPatientIdentifier();
  }
}
