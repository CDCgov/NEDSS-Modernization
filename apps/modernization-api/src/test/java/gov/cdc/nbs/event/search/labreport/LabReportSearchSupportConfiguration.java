package gov.cdc.nbs.event.search.labreport;

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
  Active<SearchableLabReport> activeSearchableLabReport() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<SearchableLabReport> availableSearchableLabReport() {
    return new Available<>();
  }
}
