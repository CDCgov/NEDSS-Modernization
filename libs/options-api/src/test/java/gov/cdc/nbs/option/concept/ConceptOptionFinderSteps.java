package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

public class ConceptOptionFinderSteps {

  @Autowired ConceptOptionFinder finder;

  @Autowired Active<Collection<ConceptOption>> found;

  @When("I retrieve all concepts for the {string} value set")
  public void i_retrieve_all_concepts_for_the_named_value_set(final String set) {
    found.active(this.finder.find(set));
  }
}
