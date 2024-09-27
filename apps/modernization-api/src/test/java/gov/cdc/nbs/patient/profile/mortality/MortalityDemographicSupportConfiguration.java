package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDate;

@Configuration
class MortalityDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<MortalityDemographic> activeMortalityDemographic(final Clock clock) {
    return new Active<>(() -> new MortalityDemographic(LocalDate.now(clock)));
  }
}
