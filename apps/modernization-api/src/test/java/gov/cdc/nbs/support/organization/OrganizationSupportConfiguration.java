package gov.cdc.nbs.support.organization;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrganizationSupportConfiguration {

  @Bean
  Active<OrganizationIdentifier> activeOrganization() {
    return new Active<>();
  }

  @Bean
  Available<OrganizationIdentifier> availableOrganization() {
    return new Available<>();
  }

}
