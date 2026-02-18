package gov.cdc.nbs.questionbank.option;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PageBuilderOptionsVerificationSteps {

  private final Active<ResultActions> response;

  PageBuilderOptionsVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Before("@page-builder-options")
  public void reset() {
    response.reset();
  }

  @Then("there are {int} options included")
  public void there_are_n_options(final int n) throws Exception {
    havingSize(n);
  }

  @Then("there aren't any options")
  @Then("there are no options available")
  public void there_are_not_any_options() throws Exception {
    havingSize(0);
  }

  private void havingSize(int n) throws Exception {
    this.response.active().andExpect(jsonPath("$.[*]", hasSize(n)));
  }

  @Then("the option named {string} is included")
  public void the_named_option_is_included(final String concept) throws Exception {
    this.response.active().andExpect(jsonPath("$.[*].name", hasItem(equalToIgnoringCase(concept))));
  }

  @Then("the option named {string} with value {string} is included")
  public void the_option_is_included(final String name, final String value) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.[*].name", hasItem(equalToIgnoringCase(name))))
        .andExpect(jsonPath("$.[*].value", hasItem(equalToIgnoringCase(value))));
  }

  @Then("the option named {string} is not included")
  public void the_option_named_is_not_included(final String concept) throws Exception {
    this.response
        .active()
        .andExpect(jsonPath("$.[*].name", hasItem(not(equalToIgnoringCase(concept)))));
  }
}
