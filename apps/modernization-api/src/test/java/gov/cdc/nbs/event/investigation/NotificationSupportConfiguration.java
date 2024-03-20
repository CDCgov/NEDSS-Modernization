package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationSupportConfiguration {

  @Bean
  Active<NotificationIdentifier> activeNotification() {
    return new Active<>();
  }

  @Bean
  Available<NotificationIdentifier> availableNotification() {
    return new Available<>();
  }
}
