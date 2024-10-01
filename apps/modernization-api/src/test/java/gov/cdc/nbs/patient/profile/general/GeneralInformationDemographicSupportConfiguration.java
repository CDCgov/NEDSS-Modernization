package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDate;

@Configuration
class GeneralInformationDemographicSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<GeneralInformationDemographic> activeGeneralInformationDemographic(final Clock clock) {
    return new Active<>(() -> new GeneralInformationDemographic(LocalDate.now(clock)));
  }
}
