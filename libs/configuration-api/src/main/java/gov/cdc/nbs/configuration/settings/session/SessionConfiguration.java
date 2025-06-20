package gov.cdc.nbs.configuration.settings.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.Duration;

@Configuration
class SessionConfiguration {

  @Bean
  @Scope("prototype")
  Session session(
      @Value("${nbs.ui.settings.session.warning:28m}") final Duration warning,
      @Value("${nbs.ui.settings.session.expiration:30m}") final Duration expiration,
      @Value("${nbs.ui.settings.session.keepAlivePath:/nbs/HomePage.do?method=loadHomePage}") final String keepAlivePath) {
    return new Session(
        warning.toMillis(),
        expiration.toMillis(),
        keepAlivePath);
  }
}
