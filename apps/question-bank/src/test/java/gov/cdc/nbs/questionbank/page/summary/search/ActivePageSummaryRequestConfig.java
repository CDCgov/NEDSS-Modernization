package gov.cdc.nbs.questionbank.page.summary.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gov.cdc.nbs.testing.support.Active;

@Configuration
public class ActivePageSummaryRequestConfig {

  @Bean
  public Active<PageSummaryRequest> pageSummaryRequest() {
    return new Active<PageSummaryRequest>();
  }
}
