package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LabReportSearchSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<LabReportFilter> activeLabReportFilter() {
    return new Active<>(LabReportFilter::new);
  }

  @Bean
  @ScenarioScope
  Active<LabReport> activeSearchableLabReport() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<LabReport> availableSearchableLabReport() {
    return new Available<>();
  }
}
