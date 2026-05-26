package gov.cdc.nbs.option.places.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PlaceListSteps {

  private final PlaceListRequester request;
  private final Active<ResultActions> response;

  PlaceListSteps(final PlaceListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the places")
  public void i_am_retrieving_all_the_places() throws Exception {
    response.active(request.complete());
  }
}
