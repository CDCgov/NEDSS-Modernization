package gov.cdc.nbs.option.facility.autocomplete;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class FacilityOptionSteps {

  private final FacilityOptionMother mother;

  FacilityOptionSteps(final FacilityOptionMother mother) {
    this.mother = mother;
  }

  @Before("@facilities")
  public void clean() {
    mother.reset();
  }

  @Given("there is a facility for {string}")
  public void there_is_a_facility_for(final String name) {
    mother.create(name);
  }

}
