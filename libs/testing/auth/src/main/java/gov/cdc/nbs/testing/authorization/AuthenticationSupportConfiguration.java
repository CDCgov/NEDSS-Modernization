package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
class AuthenticationSupportConfiguration {

  @Bean
  @ScenarioScope
  Available<ActiveUser> availableActiveUsers() {
    return new Available<>();
  }

  @Bean
  @ScenarioScope
  Active<ActiveUser> activeActiveUsers() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Active<SessionCookie> activeSession() {
    return new Active<>();
  }
}
