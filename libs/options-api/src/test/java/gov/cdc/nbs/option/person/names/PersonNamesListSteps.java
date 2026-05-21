package gov.cdc.nbs.option.person.names;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PersonNamesListSteps {

  private final PersonNamesListRequester requester;
  private final Active<ResultActions> response;

  PersonNamesListSteps(PersonNamesListRequester requester, Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @When("I am retrieving all the person names")
  public void i_am_retrieving_all_the_person_names() throws Exception {
    response.active(this.requester.request());
  }
}
