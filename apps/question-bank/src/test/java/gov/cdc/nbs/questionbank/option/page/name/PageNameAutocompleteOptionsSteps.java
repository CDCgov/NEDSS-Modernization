package gov.cdc.nbs.questionbank.option.page.name;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PageNameAutocompleteOptionsSteps {

  private final PageNameAutocompleteOptionRequester requester;
  private final Active<ResultActions> response;

  PageNameAutocompleteOptionsSteps(
      final PageNameAutocompleteOptionRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I am trying to find selectable page names that start with {string}")
  public void i_am_trying_to_find_selectable_page_names_that_start_with(final String criteria) {
    this.response.active(this.requester.complete(criteria));
  }

  @When("I am trying to find at most {int} selectable page names that start with {string}")
  public void i_am_trying_to_find_at_most_n_selectable_page_names_that_start_with(
      final int limit, final String criteria) {
    this.response.active(this.requester.complete(criteria, limit));
  }
}
