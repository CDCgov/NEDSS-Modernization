package gov.cdc.nbs.configuration.settings;

import gov.cdc.nbs.configuration.settings.analytics.Analytics;
import gov.cdc.nbs.configuration.settings.session.Session;
import gov.cdc.nbs.configuration.settings.smarty.Smarty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;



@Configuration
class SettingsConfiguration {

  @Bean
  @RequestScope
  SettingsResolver settingsResolver(
      final Smarty smarty,
      final Analytics analytics,
      final Session session
  ) {
    return () -> new Settings(smarty, analytics, session);
  }
}
