package gov.cdc.nbs.support.provider;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProviderSupportConfiguration {

  @Bean
  Active<ProviderIdentifier> activeProvider() {
    return new Active<>();
  }

  @Bean
  Available<ProviderIdentifier> availableProvider() {
    return new Available<>();
  }
}
