package gov.cdc.nbs.option.country.autocomplete;

import gov.cdc.nbs.option.AutocompleteRequester;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class CountryAutocompleteSteps {

  private static final String NAME = "countries";
  private final AutocompleteRequester request;
  private final Active<ResultActions> response;

  CountryAutocompleteSteps(
      final AutocompleteRequester request,
      final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am trying to find countries that start with {string}")
  public void i_am_trying_to_find_countries_that_start_with(final String criteria)
      throws Exception {
    response.active(request.complete(NAME, criteria));
  }

  @When("I am trying to find at most {int} countries that start with {string}")
  public void i_am_trying_to_find_n_countries_that_start_with(
      final int limit,
      final String criteria) throws Exception {
    response.active(request.complete(NAME, criteria, limit));
  }

}
