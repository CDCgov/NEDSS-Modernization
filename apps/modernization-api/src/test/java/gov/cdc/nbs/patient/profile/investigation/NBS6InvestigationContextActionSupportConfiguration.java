package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NBS6InvestigationContextActionSupportConfiguration {

  @Bean
  @ScenarioScope
  Active<NBS6InvestigationRequest> activeNbs6InvestigationContextAction() {//    PageAction.do
    return new Active<>();
  }

}
