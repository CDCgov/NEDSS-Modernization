package gov.cdc.nbs.option.facility.autocomplete;

import io.cucumber.java.en.Given;

public class FacilityOptionSteps {

  private final FacilityOptionMother mother;

  FacilityOptionSteps(final FacilityOptionMother mother) {
    this.mother = mother;
  }

  @Given("there is a facility for {string}")
  public void there_is_a_facility_for(final String name) {
    mother.create(name);
  }

  @Given("there is a facility for {string} that was added electronically")
  public void there_is_an_electronic_facility(final String name) {
    mother.electronic(name);
  }
}
