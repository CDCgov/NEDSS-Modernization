package gov.cdc.nbs.search.support;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

@Configuration
class SearchSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<SortCriteria> activeSortCriteria() {
    return new Active<>(() -> new SortCriteria(Sort.Direction.DESC, "relevance"));
  }
}
