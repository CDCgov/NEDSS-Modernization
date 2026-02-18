package gov.cdc.nbs.option.language.primary.autocomplete;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PrimaryLanguageAutocompleteSteps {

  private final PrimaryLanguageOptionAutocompleteRequester request;
  private final Active<ResultActions> response;

  PrimaryLanguageAutocompleteSteps(
      final PrimaryLanguageOptionAutocompleteRequester request,
      final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am trying to find primary languages that start with {string}")
  public void i_am_trying_to_find_options_that_start_with(final String criteria) throws Exception {
    response.active(request.complete(criteria));
  }

  @When("I am trying to find at most {int} primary language(s) that start with {string}")
  public void i_am_trying_to_find_n_options_that_start_with(final int limit, final String criteria)
      throws Exception {
    response.active(request.complete(criteria, limit));
  }
}
