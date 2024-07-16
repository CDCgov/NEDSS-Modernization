package gov.cdc.nbs.patient.profile.general.change;

import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientGeneralChangeConfiguration {

  @Bean
  Active<GeneralInformationPendingChanges> activeGeneralInformationPendingChanges() {
    return new Active<>(GeneralInformationPendingChanges::new);
  }

}
