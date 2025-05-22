package gov.cdc.nbs.event.report.morbidity;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MorbidityReportingSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<MorbidityReportIdentifier> activeMorbidityReport() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<MorbidityReportIdentifier> availableMorbidityReports() {
    return new Available<>();
  }
}
