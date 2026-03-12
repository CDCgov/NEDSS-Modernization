package gov.cdc.nbs.option.country.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class CountryListSteps {

  private final CountryListRequester request;
  private final Active<ResultActions> response;

  CountryListSteps(final CountryListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the countries")
  public void i_am_retrieving_all_the_countries() throws Exception {
    response.active(request.complete());
  }
}
