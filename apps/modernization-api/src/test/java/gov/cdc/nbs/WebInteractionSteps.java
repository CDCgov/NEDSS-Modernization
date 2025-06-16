package gov.cdc.nbs;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.hamcrest.Matchers.emptyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WebInteractionSteps {

  private final Active<ResultActions> activeAction;

  public WebInteractionSteps(
      final Active<ResultActions> activeAction
  ) {
    this.activeAction = activeAction;
  }

  @Then("I am not allowed due to insufficient permissions")
  public void access_is_denied() throws Exception {
    this.activeAction.active().andExpect(accessDenied());
  }

  @Then("I made a bad request")
  public void bad_request() throws Exception {
    this.activeAction.active().andExpect(status().isBadRequest());
  }

  @Then("it was not found")
  public void not_found() throws Exception {
    this.activeAction.active().andExpect(status().isNotFound());
  }

  @Then("I am redirected to the timeout page")
  public void i_am_redirected_to_the_timeout_page() throws Exception {
    this.activeAction.active()
        .andExpect(status().isFound())
        .andExpect(header().string("Location", "/nbs/timeout"));
  }

  @Then("no value is returned")
  public void empty() throws Exception {
    this.activeAction.active()
        .andExpect(content().string(emptyString()));
  }

  @Then("no values are returned")
  public void emptyList() throws Exception {
    this.activeAction.active()
        .andExpect(jsonPath("$").isEmpty());
  }
}
