package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.authentication.TokenCreator;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationSteps {

  private final TokenCreator tokenCreator;
  private final ActiveUserMother userMother;
  private final Active<ActiveUser> activeUser;
  private final Active<SessionCookie> activeSession;

  private final SessionMother sessionMother;

  AuthenticationSteps(
      final TokenCreator tokenCreator,
      final ActiveUserMother userMother,
      final Active<ActiveUser> activeUser,
      final Active<SessionCookie> activeSession,
      final SessionMother sessionMother) {
    this.tokenCreator = tokenCreator;
    this.userMother = userMother;
    this.activeUser = activeUser;
    this.activeSession = activeSession;
    this.sessionMother = sessionMother;
  }

  @Before
  public void clean() {
    sessionMother.reset();
  }

  @Given("A user exists")
  @Given("I am logged in")
  @Given("I have authenticated as a user")
  @Given("I am logged into NBS")
  @Given("I am logged into NBS and a security log entry exists")
  public void loggedIn() {
    ActiveUser user = userMother.create();

    activate(user);
  }

  @Given("I have not authenticated as a user")
  @Given("I am not logged in( at all)")
  public void notLoggedIn() {

    String session = activeSession.maybeActive().map(SessionCookie::identifier).orElse("NOPE");
    activeUser.maybeActive().ifPresent(current -> sessionMother.logout(current, session));

    activeUser.reset();
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  private void activate(final ActiveUser user) {
    NBSToken token = this.tokenCreator.forUser(user.username());

    ActiveUser currentUser = new ActiveUser(user.id(), user.username(), user.nedssEntry(), token);

    sessionMother.create(currentUser);

    activeUser.active(currentUser);
  }

  @Given("I am logged in as {string}")
  public void loggedInAs(final String name) {
    ActiveUser user = userMother.create(name);

    activate(user);
  }
}
