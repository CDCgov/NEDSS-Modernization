package gov.cdc.nbs.event.document;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CaseReportingSupportConfiguration {

  @Bean
  Active<CaseReportIdentifier> activeCaseReport() {
    return new Active<>();
  }

  @Bean
  Available<CaseReportIdentifier> availableCaseReports() {
    return new Available<>();
  }
}
