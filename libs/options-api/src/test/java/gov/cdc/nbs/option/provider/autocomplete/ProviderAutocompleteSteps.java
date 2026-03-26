package gov.cdc.nbs.option.provider.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ProviderAutocompleteSteps {

  private final ProviderAutocompleteRequester request;
  private final Active<ResultActions> response;

  ProviderAutocompleteSteps(
      final ProviderAutocompleteRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @Before("@providers")
  public void reset() {
    response.reset();
  }

  @When("I am trying to find providers that start with {string}")
  public void i_am_trying_to_find_providers_that_start_with(final String criteria)
      throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} providers(s) that start with {string}")
  public void i_am_trying_to_find_n_providers_that_start_with(
      final int limit, final String criteria) throws Exception {
    response.active(request.complete(criteria, limit));
  }
}
