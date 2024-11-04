package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LabReportingSupportConfiguration {

  @Bean
  Active<LabReportIdentifier> activeLabReport() {
    return new Active<>();
  }

  @Bean
  Available<LabReportIdentifier> availableLabReport() {
    return new Available<>();
  }

  @Bean
  Active<AccessionIdentifier> activeAccessionIdentifier() {
    return new Active<>();
  }
}
