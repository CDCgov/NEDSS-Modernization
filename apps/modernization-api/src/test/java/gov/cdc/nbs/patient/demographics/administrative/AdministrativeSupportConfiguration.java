package gov.cdc.nbs.patient.demographics.administrative;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDate;

@Configuration
class AdministrativeSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<Administrative> activeAdministrative(final Clock clock) {
    return new Active<>(() -> new Administrative(LocalDate.now(clock)));
  }
}
