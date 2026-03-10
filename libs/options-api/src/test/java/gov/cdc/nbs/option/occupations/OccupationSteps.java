package gov.cdc.nbs.option.occupations;

import io.cucumber.java.en.Given;

public class OccupationSteps {

  private final OccupationMother mother;

  OccupationSteps(final OccupationMother mother) {
    this.mother = mother;
  }

  @Given("there is a {string} occupation")
  public void the_occupation_exists(final String name) {
    mother.create(name);
  }
}
