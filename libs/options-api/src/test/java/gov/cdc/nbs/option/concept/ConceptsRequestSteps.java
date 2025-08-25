package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ConceptsRequestSteps {

  private final ConceptsRequest request;

  private final Active<ResultActions> response;

  ConceptsRequestSteps(final ConceptsRequest request, Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I request all concepts for the {string} value set")
  public void i_request_all_concepts_for_the_named_value_set(final String set) throws Exception {
    response.active(request.request(set));
  }

}
