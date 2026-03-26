package gov.cdc.nbs.search.redirect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SearchRedirectConfiguration {

  @Bean
  SearchRedirect searchRedirect() {
    return new SearchRedirect("/search");
  }
}
