package gov.cdc.nbs.concept;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class ConceptResolverSteps {

  @Autowired
  ConceptResolver resolver;

  @Autowired
  Active<Collection<Concept>> found;

  @When("I retrieve any concept in the {string} value set with a name starting with {string}")
  public void i_retrieve_all_concepts_for_the_named_value_set(
      final String set,
      final String criteria
  ) {
    found.active(this.resolver.resolve(set, criteria, 15));
  }
}
