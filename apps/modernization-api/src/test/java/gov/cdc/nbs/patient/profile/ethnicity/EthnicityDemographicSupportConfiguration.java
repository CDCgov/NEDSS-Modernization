package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDate;

@Configuration
class EthnicityDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<EthnicityDemographic> activeEthnicityDemographic(final Clock clock) {
    return new Active<>(() -> new EthnicityDemographic(LocalDate.now(clock)));
  }
}
