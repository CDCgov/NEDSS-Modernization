package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;

@Configuration
class PatientCreateSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<NewPatient> activeNewPatient(final Clock clock) {
    return new Active<>(() -> new NewPatient(Instant.now(clock)));
  }
}
