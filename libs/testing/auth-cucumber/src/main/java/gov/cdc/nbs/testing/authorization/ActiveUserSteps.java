package gov.cdc.nbs.testing.authorization;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SuppressWarnings("java:S100")
public class ActiveUserSteps {

  private final ActiveUserMother mother;

  private final ActiveUserParameterResolver resolver;

  ActiveUserSteps(
      final ActiveUserMother mother,
      final ActiveUserParameterResolver resolver
  ) {
    this.mother = mother;
    this.resolver = resolver;
  }

  @ParameterType(name = "user", value = ".*")
  public ActiveUser user(final String username) {
    return this.resolver.resolve(username).orElse(null);
  }

  @Given("the {string} user exists")
  public void the_user_exists(final String name) {
    mother.create(name);
  }

  @Given("the user {string} {string} exists as {string}")
  public void the_user_named_exists(final String first, final String last, final String username) {
    mother.create(username, first, last);
  }
}
