package gov.cdc.nbs.option.jurisdiction;

import io.cucumber.java.en.Given;

public class JurisdictionOptionSteps {

  private final JurisdictionMother mother;


  JurisdictionOptionSteps(final JurisdictionMother mother) {
    this.mother = mother;
  }

  @Given("there is a {string} jurisdiction")
  public void the_jurisdiction_exists_in_the_value_set(final String name) {
    mother.create(name);
  }

}
