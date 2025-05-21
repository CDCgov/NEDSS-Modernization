package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LabReportingSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<LabReportIdentifier> activeLabReport() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<LabReportIdentifier> availableLabReport() {
    return new Available<>();
  }

  @Bean
  @ScenarioScope
  Active<AccessionIdentifier> activeAccessionIdentifier() {
    return new Active<>();
  }
}
