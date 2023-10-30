package gov.cdc.nbs;

import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.controller.UserController;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class FindUsersSteps {

  @Autowired
  private UserController userController;

  @Autowired
  Available<ActiveUser> availableUsers;

  @Autowired
  Authenticated authenticated;

  private Page<AuthUser> users;

  @When("I retrieve the user list")
  public void i_search_for_users() {
    users = authenticated.perform(() -> userController.findAllUsers(null));
  }

  @Then("The {string} user is returned")
  public void a_user_is_returned(final String expected) {

    ActiveUser authorizedUser = this.availableUsers.all().filter(u -> Objects.equals(expected, u.username()))
        .findFirst()
        .orElseThrow();

    assertThat(users.getContent())
        .anySatisfy(actual -> assertThat(actual).returns(authorizedUser.id(), AuthUser::getId));
  }

  @Then("The {string} user is not returned")
  public void a_user_is_not_returned(final String expected) {
    ActiveUser authorizedUser = this.availableUsers.all().filter(u -> Objects.equals(expected, u.username()))
        .findFirst()
        .orElseThrow();

    assertThat(users.getContent())
        .noneSatisfy(actual -> assertThat(actual).returns(authorizedUser.id(), AuthUser::getId));
  }

}
