package gov.cdc.nbs.option;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class OptionsVerificationSteps {


  private final Active<ResultActions> response;

  OptionsVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then("there is {int} option included")
  @Then("there are {int} options included")
  public void there_are_n_options(final int n) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$", hasSize(n)));
  }

  @Then("the option named {string} is included")
  public void the_named_option_is_included(final String concept) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].name", hasItem(equalToIgnoringCase(concept))));
  }

  @Then("the option named {string} is not included")
  public void the_option_named_is_not_included(final String concept) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[*].name", hasItem(not(equalToIgnoringCase(concept)))));
  }

  @Then("there are options available")
  public void there_are_options_available() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$", not(hasSize(0))));
  }

  @Then("there aren't any options available")
  public void there_are_not_any_options() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Then("the {nth} option is {string}")
  public void the_nth_option_is(final int position, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%s].name", position - 1).value(equalToIgnoringCase(value)));
  }
}
