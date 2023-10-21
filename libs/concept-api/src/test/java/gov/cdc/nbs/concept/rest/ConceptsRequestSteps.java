package gov.cdc.nbs.concept.rest;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

public class ConceptsRequestSteps {

  @Autowired
  ConceptsRequest request;

  @Autowired
  Active<ResultActions> response;

  @Before("@request")
  public void reset() {
    response.reset();
  }

  @When("I request all concepts for the {string} value set")
  public void i_request_all_concepts_for_the_named_value_set(final String set) throws Exception {
    response.active(request.request(set));
  }

}
