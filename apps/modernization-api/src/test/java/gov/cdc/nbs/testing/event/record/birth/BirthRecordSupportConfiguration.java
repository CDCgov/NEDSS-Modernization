package gov.cdc.nbs.testing.event.record.birth;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class BirthRecordSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<BirthRecordIdentifier> activeBirthRecords() {
    return new Active<>();
  }
}
