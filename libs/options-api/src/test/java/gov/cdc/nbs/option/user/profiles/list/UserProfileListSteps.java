package gov.cdc.nbs.option.user.profiles.list;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class UserProfileListSteps {

  private final UserProfileListRequester request;
  private final Active<ResultActions> response;

  UserProfileListSteps(
      final UserProfileListRequester request, final Active<ResultActions> response) {
    this.request = request;
    this.response = response;
  }

  @When("I am retrieving all the user profiles")
  public void i_am_retrieving_all_the_user_profiles() throws Exception {
    response.active(request.complete());
  }
}
