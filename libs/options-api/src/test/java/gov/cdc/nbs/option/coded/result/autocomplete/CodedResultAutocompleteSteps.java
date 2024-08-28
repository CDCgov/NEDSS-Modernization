package gov.cdc.nbs.option.coded.result.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class CodedResultAutocompleteSteps {

  private final CodedResultAutocompleteRequester request;
  private final Active<ResultActions> response;

  CodedResultAutocompleteSteps(
      final CodedResultAutocompleteRequester request,
      final Active<ResultActions> response
  ) {
    this.request = request;
    this.response = response;
  }

  @When("I am trying to find coded results that start with {string}")
  public void i_am_trying_to_find_coded_results_that_start_with(final String criteria) throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} coded result(s) that start with {string}")
  public void i_am_trying_to_find_n_coded_results_that_start_with(
      final int limit,
      final String criteria
  ) throws Exception {
    response.active(request.complete(criteria, limit));
  }

}
