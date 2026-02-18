package gov.cdc.nbs.option.user.autocomplete;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class UserOptionSteps {

  private final UserOptionMother mother;

  UserOptionSteps(final UserOptionMother mother) {
    this.mother = mother;
  }

  @Before("@condition")
  public void clean() {
    mother.reset();
  }

  @Given("there is a user for {string} {string}")
  public void there_is_a_condition(final String first, final String last) {
    mother.create(first, last);
  }
}
