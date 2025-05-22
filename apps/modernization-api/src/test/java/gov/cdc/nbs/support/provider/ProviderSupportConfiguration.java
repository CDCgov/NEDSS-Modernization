package gov.cdc.nbs.support.provider;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProviderSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<ProviderIdentifier> activeProvider() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<ProviderIdentifier> availableProvider() {
    return new Available<>();
  }
}
