package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
@ComponentScan
class AuthenticationSupportConfiguration {

  @Bean
  AuthenticationSupportSettings authenticationSettings(
      final @Value("${testing.data.createdBy:9999}") long createdBy) {
    return new AuthenticationSupportSettings(
        createdBy,
        Instant.now()
    );
  }

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
