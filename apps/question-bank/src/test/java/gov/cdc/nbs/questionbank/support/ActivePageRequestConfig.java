package gov.cdc.nbs.questionbank.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import gov.cdc.nbs.testing.support.Active;

@Configuration
public class ActivePageRequestConfig {

  @Bean
  public Active<PageRequest> pageable() {
    return new Active<PageRequest>();
  }
}


