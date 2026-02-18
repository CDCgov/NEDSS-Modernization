package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VaccinationSupportConfiguration {

  @Bean
  Active<VaccinationIdentifier> activeVaccination() {
    return new Active<>();
  }
}
