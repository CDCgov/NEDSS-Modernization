package gov.cdc.nbs.option.county;

import io.cucumber.java.en.Given;

public class CountyOptionSteps {

  private final CountyMother mother;


  CountyOptionSteps(final CountyMother mother) {
    this.mother = mother;
  }

  @Given("there is a {string} county for state {state}")
  public void the_county_exists_in_the_value_set(final String name, final String state) {
    mother.create(name, state);
  }

}
