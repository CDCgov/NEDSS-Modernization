package gov.cdc.nbs.patient.profile.gender;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDate;

@Configuration
class GenderDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<GenderDemographic> activeAdministrative(final Clock clock) {
    return new Active<>(() -> new GenderDemographic(LocalDate.now(clock)));
  }
}