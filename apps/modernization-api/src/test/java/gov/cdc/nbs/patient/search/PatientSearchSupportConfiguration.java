package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientSearchSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<PatientFilter> activePatientFilter() {
    return new Active<>(PatientFilter::new);
  }

}
