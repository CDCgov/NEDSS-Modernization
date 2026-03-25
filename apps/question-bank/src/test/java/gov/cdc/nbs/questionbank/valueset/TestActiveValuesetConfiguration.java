package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestActiveValuesetConfiguration {

  @Bean
  static Active<Codeset> activeValueset() {
    return new Active<>();
  }
}
