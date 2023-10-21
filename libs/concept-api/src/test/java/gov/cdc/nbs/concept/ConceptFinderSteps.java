package gov.cdc.nbs.concept;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class ConceptFinderSteps {

  @Autowired
  ConceptFinder finder;

  @Autowired
  Active<Collection<Concept>> found;

  @When("I retrieve all concepts for the {string} value set")
  public void i_retrieve_all_concepts_for_the_named_value_set(final String set) {
    found.active(this.finder.find(set));
  }
}
