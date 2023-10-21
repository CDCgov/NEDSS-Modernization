package gov.cdc.nbs.concept.rest;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ConceptsResponseVerificationSteps {

  @Autowired
  Active<ResultActions> response;


  @Then("there are {int} concepts returned")
  public void there_are_n_concepts_returned(final int n) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.concepts", hasSize(n)));
  }

  @Then("the response includes the {string} concept")
  public void the_named_concept_is_included(final String concept) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.concepts[*].name", hasItem(equalToIgnoringCase(concept))));
  }

  @Then("the response does not include the {string} concept")
  public void the_named_concept_is_not_included(final String concept) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.concepts[*].name", hasItem(not(equalToIgnoringCase(concept)))));
  }

  @Then("the response does not include any concepts")
  public void the_response_does_not_include_any_concepts() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.concepts[*]", hasSize(0)));
  }
}
