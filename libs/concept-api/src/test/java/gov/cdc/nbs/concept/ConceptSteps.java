package gov.cdc.nbs.concept;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class ConceptSteps {

  @Autowired
  ConceptMother mother;

  @Before
  public void clean() {
    mother.reset();
  }

  @Given("the {string} concept is in the {string} value set")
  public void the_concept_exists_in_the_value_set(final String concept, final String set) {
    mother.create(concept, set);
  }

}
