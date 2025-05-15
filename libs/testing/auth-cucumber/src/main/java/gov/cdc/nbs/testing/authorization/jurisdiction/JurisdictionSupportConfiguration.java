package gov.cdc.nbs.testing.authorization.jurisdiction;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JurisdictionSupportConfiguration {

  @Bean
  Active<JurisdictionIdentifier> activeJurisdiction() {
    return new Active<>(JurisdictionSupportConfiguration::defaultJurisdiction);
  }

  @Bean
  Available<JurisdictionIdentifier> availableJurisdiction() {
    return new Available<>(defaultJurisdiction());
  }


  private static JurisdictionIdentifier defaultJurisdiction() {
    return new JurisdictionIdentifier(
        13002,
        "999999"
    );
  }
}
