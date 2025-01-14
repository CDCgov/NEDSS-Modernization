package gov.cdc.nbs;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WebInteractionSteps {

  private final Active<MockHttpServletResponse> activeResponse;
  private final Active<ResultActions> activeAction;

  public WebInteractionSteps(
      final Active<MockHttpServletResponse> activeResponse,
      final Active<ResultActions> activeAction
  ) {
    this.activeResponse = activeResponse;
    this.activeAction = activeAction;
  }


  @Before("@web-interaction")
  public void reset() {
    activeResponse.reset();
    activeAction.reset();
  }

  @Then("I am not allowed due to insufficient permissions")
  public void access_is_denied() throws Exception {
    this.activeAction.active().andExpect(accessDenied());
  }

  @Then("I made a bad request")
  public void bad_request() throws Exception {
    this.activeAction.active().andExpect(status().isBadRequest());
  }

  @Then("I am redirected to the timeout page")
  public void i_am_redirected_to_the_timeout_page() throws Exception {
    this.activeAction.active()
        .andExpect(status().isFound())
        .andExpect(header().string("Location", "/nbs/timeout"));
  }
}
