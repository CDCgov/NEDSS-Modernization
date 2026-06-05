package gov.cdc.nbs.option.user.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class UserListSteps {

  private final UserListRequester request;
  private final Active<ResultActions> response;

  UserListSteps(final UserListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the users")
  public void i_am_retrieving_all_the_users() throws Exception {
    response.active(request.complete());
  }
}
