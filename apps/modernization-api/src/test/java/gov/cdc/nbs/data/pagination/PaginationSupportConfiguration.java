package gov.cdc.nbs.data.pagination;

import gov.cdc.nbs.testing.support.Active;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
class PaginationSupportConfiguration {

  @Bean
  Active<Pageable> activePageable(@Value("${nbs.search.max-page-size}") final int size) {
    return new Active<>(() -> Pageable.ofSize(size));
  }
}
