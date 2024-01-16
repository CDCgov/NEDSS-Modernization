package gov.cdc.nbs;

import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

@Configuration
class WebInteractionConfiguration {

  @Bean
  Active<ResultActions> resultActionsActive() {
    return new Active<>();
  }

  @Bean
  Active<MockHttpServletResponse> mockHttpServletResponseActive() {
    return new Active<>();
  }
}
