package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDate;

@Configuration
class BirthDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<BirthDemographic> activeBirthDemographic(final Clock clock) {
    return new Active<>(() -> new BirthDemographic(LocalDate.now(clock)));
  }
}
