package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.option.concept.autocomplete.ConceptOptionResolver;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

public class ConceptOptionResolverSteps {

  @Autowired ConceptOptionResolver resolver;

  @Autowired Active<Collection<ConceptOption>> found;

  @When("I retrieve any concept in the {string} value set with a name starting with {string}")
  public void i_retrieve_all_concepts_for_the_named_value_set(
      final String set, final String criteria) {
    found.active(this.resolver.resolve(set, criteria, 15));
  }
}
