package gov.cdc.nbs.authorization;

import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
class TestActiveUserDetailsConfiguration {

  @Bean
  static Active<UserDetails> userDetailsTestActive() {
    return new Active<>();
  }
}
