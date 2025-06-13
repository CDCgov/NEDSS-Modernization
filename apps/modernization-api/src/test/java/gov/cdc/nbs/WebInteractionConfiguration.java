package gov.cdc.nbs;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

@Configuration
class WebInteractionConfiguration {

  @Bean
  @ScenarioScope
  Active<ResultActions> resultActionsActive() {
    return new Active<>();
  }

  @Bean
  @ScenarioScope
  Active<MockHttpServletResponse> mockHttpServletResponseActive() {
    return new Active<>();
  }
}
