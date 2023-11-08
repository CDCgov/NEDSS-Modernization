package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.TokenCreator;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("java:S100")
@Transactional
public class AuthenticationSteps {


  private final TokenCreator tokenCreator;
  private final ActiveUserMother mother;
  private final Active<ActiveUser> activeUser;

  public AuthenticationSteps(
      final TokenCreator tokenCreator,
      final ActiveUserMother mother,
      final Active<ActiveUser> activeUser
  ) {
    this.tokenCreator = tokenCreator;
    this.mother = mother;
    this.activeUser = activeUser;
  }

  @Before
  public void clean() {
    mother.reset();
  }

  @Given("I am logged in")
  public void i_am_logged_in() {
    ActiveUser user = mother.create();

    activate(user);
  }

  @Given("I am not logged in( at all)")
  public void i_am_not_logged_in() {
    activeUser.reset();
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  private void activate(final ActiveUser user) {
    NBSToken token = this.tokenCreator.forUser(user.username());

    ActiveUser currentUser = new ActiveUser(user.id(), user.username(), user.nedssEntry(), token);
    activeUser.active(currentUser);
  }

  @Given("I am logged in as {string}")
  public void i_am_logged_in_as(final String name) {
    ActiveUser user = mother.create(name);

    activate(user);
  }
}
