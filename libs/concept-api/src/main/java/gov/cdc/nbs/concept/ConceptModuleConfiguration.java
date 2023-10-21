package gov.cdc.nbs.concept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
class ConceptModuleConfiguration {

  @Bean
  ConceptFinder conceptFinder(final JdbcTemplate template) {
    return new JDBCConceptFinder(template);
  }

  @Bean
  ConceptResolver conceptResolver(final JdbcTemplate template) {
    return new JDBCConceptResolver(new NamedParameterJdbcTemplate(template));
  }
}
