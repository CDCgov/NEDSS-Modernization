package gov.cdc.nbs.testing.authorization.programarea;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProgramAreaSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<ProgramAreaIdentifier> activeProgramArea() {
    return new Active<>(ProgramAreaSupportConfiguration::defaultProgramArea);
  }

  @Bean
  @ScenarioScope
  Available<ProgramAreaIdentifier> availableProgramArea() {
    return new Available<>(defaultProgramArea());
  }

  private static ProgramAreaIdentifier defaultProgramArea() {
    return new ProgramAreaIdentifier(15, "STD", "STD");
  }
}
