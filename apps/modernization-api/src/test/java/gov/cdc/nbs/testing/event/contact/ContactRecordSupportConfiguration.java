package gov.cdc.nbs.testing.event.contact;

import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class ContactRecordSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<ContactRecordIdentifier> activeContactRecord() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Available<ContactRecordIdentifier> availableContactRecords() {
    return new Available<>();
  }

}
