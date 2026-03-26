package gov.cdc.nbs.configuration.settings.analytics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class AnalyticsConfiguration {

  @Bean
  @Scope("prototype")
  Analytics analytics(
      @Value("${nbs.ui.settings.analytics.host:https://us.i.posthog.com}") final String host,
      @Value("${nbs.ui.settings.analytics.key:#{null}}") final String key) {
    return new Analytics(key, host);
  }
}
