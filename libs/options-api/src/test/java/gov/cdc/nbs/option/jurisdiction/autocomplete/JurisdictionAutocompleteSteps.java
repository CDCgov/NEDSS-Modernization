package gov.cdc.nbs.option.jurisdiction.autocomplete;

import gov.cdc.nbs.option.AutocompleteRequester;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class JurisdictionAutocompleteSteps {

  private static final String NAME = "jurisdictions";
  private final AutocompleteRequester request;
  private final Active<ResultActions> response;

  JurisdictionAutocompleteSteps(
      final AutocompleteRequester request,
      final Active<ResultActions> response
  ) {
    this.request = request;
    this.response = response;
  }

  @When("I am trying to find jurisdictions that start with {string}")
  public void i_am_trying_to_find_jurisdiction_that_start_with(final String criteria) throws Exception {
    response.active(request.complete(NAME, criteria));
  }

  @When("I am trying to find at most {int} jurisdiction(s) that start with {string}")
  public void i_am_trying_to_find_n_jurisdiction_that_start_with(
      final int limit,
      final String criteria
  ) throws Exception {
    response.active(request.complete("jurisdictions", criteria, limit));
  }

}
