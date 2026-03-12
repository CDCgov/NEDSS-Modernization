package gov.cdc.nbs.support.organization;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrganizationSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<OrganizationIdentifier> activeOrganization() {
    return new Active<>(() -> new OrganizationIdentifier(10003001L));
  }

  @Bean
  @ScenarioScope
  Available<OrganizationIdentifier> availableOrganization() {
    return new Available<>();
  }
}
