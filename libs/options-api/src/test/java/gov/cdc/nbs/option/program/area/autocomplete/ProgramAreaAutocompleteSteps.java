package gov.cdc.nbs.option.program.area.autocomplete;

import gov.cdc.nbs.option.AutocompleteRequester;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class ProgramAreaAutocompleteSteps {

  private static final String NAME = "program-areas";
  private final AutocompleteRequester request;
  private final Active<ResultActions> response;

  ProgramAreaAutocompleteSteps(
      final AutocompleteRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am trying to find program areas that start with {string}")
  public void i_am_trying_to_find_jurisdiction_that_start_with(final String criteria)
      throws Exception {
    response.active(request.complete(NAME, criteria));
  }

  @When("I am trying to find at most {int} program area(s) that start with {string}")
  public void i_am_trying_to_find_n_jurisdiction_that_start_with(
      final int limit, final String criteria) throws Exception {
    response.active(request.complete("program-areas", criteria, limit));
  }
}
