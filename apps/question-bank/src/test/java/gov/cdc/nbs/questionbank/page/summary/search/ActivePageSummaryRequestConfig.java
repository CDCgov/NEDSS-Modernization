package gov.cdc.nbs.questionbank.page.summary.search;

import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivePageSummaryRequestConfig {

  @Bean
  public Active<PageSummaryRequest> pageSummaryRequest() {
    return new Active<PageSummaryRequest>();
  }
}
