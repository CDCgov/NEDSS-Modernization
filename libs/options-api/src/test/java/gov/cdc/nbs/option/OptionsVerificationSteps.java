package gov.cdc.nbs.option;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class OptionsVerificationSteps {

  @Autowired
  Active<ResultActions> response;

  @Then("there are {int} option(s) included")
  public void there_are_n_options(final int n) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.options", hasSize(n)));
  }

  @Then("the option named {string} is included")
  public void the_named_option_is_included(final String concept) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.options[*].name", hasItem(equalToIgnoringCase(concept))));
  }

  @Then("the option named {string} is not included")
  public void the_option_named_is_not_included(final String concept) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.options[*].name", hasItem(not(equalToIgnoringCase(concept)))));
  }

  @Then("there aren't any options")
  public void there_are_not_any_options() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.options[*]", hasSize(0)));
  }
}
