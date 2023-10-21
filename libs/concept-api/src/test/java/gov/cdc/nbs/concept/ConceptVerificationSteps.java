package gov.cdc.nbs.concept;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class ConceptVerificationSteps {

  @Autowired
  Active<Collection<Concept>> found;

  @Then("the {string} concept is included")
  public void the_named_concept_is_included(final String concept) {
    assertThat(found.active()).anySatisfy(
        actual -> assertThat(actual.name()).isEqualTo(concept)
    );
  }

  @Then("the {string} concept is not included")
  public void the_named_concept_is_not_included(final String concept) {
    assertThat(found.active()).noneSatisfy(
        actual -> assertThat(actual.name()).isEqualTo(concept)
    );
  }

  @Then("there are {int} concepts found")
  public void there_are_n_concepts_found(final int n) {
    assertThat(found.active()).hasSize(n);
  }
}
