package gov.cdc.nbs.testing.event.treatment;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class TreatmentSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<TreatmentIdentifier> activeTreatment() {
    return new Active<>();
  }
}
