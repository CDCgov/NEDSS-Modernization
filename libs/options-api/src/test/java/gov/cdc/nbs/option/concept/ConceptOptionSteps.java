package gov.cdc.nbs.option.concept;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class ConceptOptionSteps {

  private final ConceptOptionMother mother;

  ConceptOptionSteps(final ConceptOptionMother mother) {
    this.mother = mother;
  }

  @Before
  public void clean() {
    mother.reset();
  }

  @Given("the {string} concept is in the {string} value set")
  public void the_concept_exists_in_the_value_set(final String concept, final String set) {
    mother.create(concept, set);
  }

}
