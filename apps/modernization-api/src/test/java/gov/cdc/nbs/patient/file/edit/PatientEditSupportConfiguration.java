package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientEditSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<EditedPatient> activeEditedPatient() {
    return new Active<>(EditedPatient::new);
  }
}
