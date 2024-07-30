package gov.cdc.nbs.patient.search.redirect;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SearchRedirectConfiguration {


  @Bean
  @ConditionalOnProperty(prefix = "nbs.ui.features.search.view", name = "enabled", havingValue = "true")
  SearchRedirect searchRedirect() {
    return new SearchRedirect("/search");
  }

  @Bean
  @ConditionalOnMissingBean
  SearchRedirect searchRedirectDefault() {
    return new SearchRedirect("/advanced-search");
  }

}
