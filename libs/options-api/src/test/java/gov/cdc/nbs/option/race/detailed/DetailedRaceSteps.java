package gov.cdc.nbs.option.race.detailed;

import io.cucumber.java.en.Given;

public class DetailedRaceSteps {

  private final DetailedRaceConceptMother mother;

  DetailedRaceSteps(DetailedRaceConceptMother mother) {
    this.mother = mother;
  }

  @Given("the {string} is a race concept")
  public void the_concept_is_a_race_concept(final String concept) {
    mother.create(concept);
  }
}
