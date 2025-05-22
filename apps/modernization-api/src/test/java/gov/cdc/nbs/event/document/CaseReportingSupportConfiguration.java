package gov.cdc.nbs.event.document;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CaseReportingSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<CaseReportIdentifier> activeCaseReport() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<CaseReportIdentifier> availableCaseReports() {
    return new Available<>();
  }
}
