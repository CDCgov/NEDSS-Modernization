package gov.cdc.nbs.option.concept;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class ConceptSteps {

  @Autowired
  ConceptMother mother;

  @Autowired
  RaceConceptMother raceConceptMother;

  @Before
  public void clean() {
    mother.reset();
    raceConceptMother.reset();
  }

  @Given("the {string} concept is in the {string} value set")
  public void the_concept_exists_in_the_value_set(final String concept, final String set) {
    mother.create(concept, set);
  }

  @Given("the {string} is a race concept")
  public void the_concept_is_a_race_concept(final String concept) {
    raceConceptMother.create(concept);
  }

}
