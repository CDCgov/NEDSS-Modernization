package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.authorization.ActiveUserMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("java:S100")
@Transactional
public class AuthenticationSteps {

  @Autowired
  ActiveUserMother mother;

  @Autowired
  Active<ActiveUser> activeUser;

  @Before
  public void clean() {
    mother.reset();
  }

  @Given("the {string} user exists")
  public void the_user_exists(final String name) {
    mother.create(name);
  }

  @Given("I am logged in")
  public void i_am_logged_in() {
    ActiveUser user = mother.create();

    ActiveUser currentUser = new ActiveUser(user.id(), user.username(), user.nedssEntry());
    activeUser.active(currentUser);
  }

  @Given("I am logged in as {string}")
  public void i_am_logged_in_as(final String name) {
    ActiveUser user = mother.create(name);

    ActiveUser currentUser = new ActiveUser(user.id(), user.username(), user.nedssEntry());
    activeUser.active(currentUser);
  }
}
