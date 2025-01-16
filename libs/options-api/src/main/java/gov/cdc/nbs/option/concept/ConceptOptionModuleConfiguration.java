package gov.cdc.nbs.option.concept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class ConceptOptionModuleConfiguration {

  @Bean
  ConceptOptionFinder conceptFinder(final JdbcTemplate template) {
    return new CodeValueGeneralConceptOptionFinder(template);
  }

}
