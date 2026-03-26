package gov.cdc.nbs.questionbank.option.page.name;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PageNameOptionsSteps {

  private final PageNameOptionRequester requester;
  private final Active<ResultActions> response;

  PageNameOptionsSteps(
      final PageNameOptionRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I retrieve the selectable page names")
  public void i_retrieve_the_selectable_page_names() {
    this.response.active(this.requester.request());
  }
}
