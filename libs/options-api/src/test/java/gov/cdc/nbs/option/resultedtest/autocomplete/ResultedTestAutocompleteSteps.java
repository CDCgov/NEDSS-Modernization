package gov.cdc.nbs.option.resultedtest.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ResultedTestAutocompleteSteps {

  private final ResultedTestAutocompleteRequester request;
  private final Active<ResultActions> response;

  ResultedTestAutocompleteSteps(
      final ResultedTestAutocompleteRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @Before("@resultedtests")
  public void reset() {
    response.reset();
  }

  @When("I am trying to find resulted tests that start with {string}")
  public void i_am_trying_to_find_resulted_tests_that_start_with(final String criteria)
      throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} resulted tests(s) that start with {string}")
  public void i_am_trying_to_find_n_resulted_tests_that_start_with(
      final int limit, final String criteria) throws Exception {
    response.active(request.complete(criteria, limit));
  }
}
