package gov.cdc.nbs.patient.profile.vaccination;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gov.cdc.nbs.testing.support.Active;

@Configuration
public class VaccinationConfiguration {

  @Bean
  public Active<VaccinationIdentifier> activeVaccination() {
    return new Active<>();
  }
}
