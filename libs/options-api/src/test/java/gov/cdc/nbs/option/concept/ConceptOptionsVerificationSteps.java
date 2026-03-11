package gov.cdc.nbs.option.concept;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class ConceptOptionsVerificationSteps {

  private final Active<ResultActions> response;

  ConceptOptionsVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("there are {int} concept(s) included")
  public void there_are_n_options(final int n) throws Exception {
    this.response.active().andExpect(jsonPath("$.options", hasSize(n)));
  }

  @Then("the concept named {string} is included")
  public void the_named_option_is_included(final String concept) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.options[*].name", hasItem(equalToIgnoringCase(concept))));
  }

  @Then("the concept named {string} is not included")
  public void the_option_named_is_not_included(final String concept) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.options[*].name", not(hasItem(equalToIgnoringCase(concept)))));
  }

  @Then("there are concepts available")
  public void there_are_options_available() throws Exception {
    this.response.active().andExpect(jsonPath("$.options[*]", not(hasSize(0))));
  }

  @Then("there aren't any concepts available")
  public void there_are_not_any_options() throws Exception {
    this.response.active().andExpect(jsonPath("$.options[*]", hasSize(0)));
  }
}
