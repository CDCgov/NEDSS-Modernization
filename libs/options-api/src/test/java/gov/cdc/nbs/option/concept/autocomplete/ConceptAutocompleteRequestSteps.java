package gov.cdc.nbs.option.concept.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

public class ConceptAutocompleteRequestSteps {

  @Autowired ConceptAutocompleteRequest request;

  @Autowired Active<ResultActions> response;

  @Before("@autocomplete")
  public void reset() {
    response.reset();
  }

  @When("I am trying to find concepts in the {string} value set that start with {string}")
  public void i_am_trying_to_find_concepts_in_the_named_value_set_that_start_with(
      final String set, final String criteria) throws Exception {
    response.active(request.complete(set, criteria));
  }

  @When(
      "I am trying to find at most {int} concept(s) in the {string} value set that start with {string}")
  public void i_am_trying_to_find_n_concepts_in_the_named_value_set_that_start_with(
      final int limit, final String set, final String criteria) throws Exception {
    response.active(request.complete(set, criteria, limit));
  }
}
