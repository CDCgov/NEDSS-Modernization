package gov.cdc.nbs.data.pagination;

import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
class PaginationSupportConfiguration {

  @Bean
  Active<Pageable> activePageable() {
    return new Active<>();
  }
}
