package gov.cdc.nbs.codes.user;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;


import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class FindUsersSteps {

  private final Active<Pageable> activePageable;
  private final UserListRequester requester;
  private final Active<ResultActions> response;

  FindUsersSteps(
      final Active<Pageable> activePageable,
      final UserListRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePageable = activePageable;
    this.requester = requester;
    this.response = response;
  }

  @When("I retrieve the user list")
  public void i_search_for_users() {
    this.activePageable.maybeActive().ifPresentOrElse(
        this::withPage,
        this::withDefaultPaging
    );
  }

  private void withDefaultPaging() {
    this.response.active(
        this.requester.users()
    );
  }

  private void withPage(final Pageable page) {
    this.response.active(
        this.requester.users(page)
    );
  }

  @Then("The {string} user is returned")
  public void a_user_is_returned(final String expected) throws Exception {
    this.response.active()
        .andDo(print())
        .andExpect(
            jsonPath("$.data.findAllUsers.content[*].userId")
                .value(hasItem(expected))
        );
  }

  @Then("The {string} user is not returned")
  public void a_user_is_not_returned(final String expected) throws Exception {
    this.response.active()
        .andDo(print())
        .andExpect(
            jsonPath("$.data.findAllUsers.content[*].userId")
                .value(not(hasItem(expected)))
        );
  }

}
