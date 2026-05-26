package gov.cdc.nbs.option.places;

import io.cucumber.java.en.Given;

public class PlaceOptionSteps {

  private final PlaceOptionMother mother;

  PlaceOptionSteps(final PlaceOptionMother mother) {
    this.mother = mother;
  }

  @Given("there is a place for {string}")
  public void there_is_a_provider(final String name) {
    mother.create(name);
  }
}
