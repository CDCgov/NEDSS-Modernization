package gov.cdc.nbs.option.concept.autocomplete;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
class ConceptOptionAutocompleteModuleConfiguration {

  @Bean
  ConceptOptionResolver conceptOptionResolver(final JdbcTemplate template) {
    return new JDBCConceptOptionResolver(new NamedParameterJdbcTemplate(template));
  }
}
