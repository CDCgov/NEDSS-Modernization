package gov.cdc.nbs.option.facility.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class FacilityAutocompleteSteps {

  private final FacilityAutocompleteRequester request;
  private final Active<ResultActions> response;

  FacilityAutocompleteSteps(
      final FacilityAutocompleteRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @Before("@facilities")
  public void reset() {
    response.reset();
  }

  @When("I am trying to find facilities that start with {string}")
  public void i_am_trying_to_find_facilities_that_start_with(final String criteria)
      throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} facilities(s) that start with {string}")
  public void i_am_trying_to_find_n_facilities_that_start_with(
      final int limit, final String criteria) throws Exception {
    response.active(request.complete(criteria, limit));
  }
}
