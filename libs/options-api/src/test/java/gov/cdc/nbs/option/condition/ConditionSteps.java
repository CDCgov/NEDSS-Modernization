package gov.cdc.nbs.option.condition;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class ConditionSteps {

  private final ConditionMother mother;

  ConditionSteps(final ConditionMother mother) {
    this.mother = mother;
  }

  @Before("@condition")
  public void clean() {
    mother.reset();
  }

  @Given("there is a {string} condition")
  public void there_is_a_condition(final String name) {
    mother.create(name);
  }
}
