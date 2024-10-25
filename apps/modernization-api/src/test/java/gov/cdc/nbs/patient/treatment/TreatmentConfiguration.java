package gov.cdc.nbs.patient.treatment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gov.cdc.nbs.testing.support.Active;

@Configuration
class TreatmentConfiguration {

  @Bean
  Active<TreatmentIdentifier> activeTreatment() {
    return new Active<>();
  }
}
