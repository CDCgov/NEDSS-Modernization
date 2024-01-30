package gov.cdc.nbs.event.report.morbidity;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MorbidityReportingSupportConfiguration {

  @Bean
  Active<MorbidityReportIdentifier> activeMorbidityReport() {
    return new Active<>();
  }

  @Bean
  Available<MorbidityReportIdentifier> availableMorbidityReports() {
    return new Available<>();
  }
}
