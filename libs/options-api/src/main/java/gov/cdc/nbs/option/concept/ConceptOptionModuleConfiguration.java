package gov.cdc.nbs.option.concept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
class ConceptOptionModuleConfiguration {

  @Bean
  ConceptOptionFinder conceptFinder(final JdbcClient client) {
    return new CodeValueGeneralConceptOptionFinder(client);
  }

}
