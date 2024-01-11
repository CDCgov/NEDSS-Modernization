package gov.cdc.nbs.questionbank.valueset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import gov.cdc.nbs.questionbank.valueset.response.RaceConcept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.support.ConceptHolder;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SearchForConceptsSteps {

  @Autowired
  private ConceptHolder conceptHolder;

  @Autowired
  private ExceptionHolder exceptionHolder;

  @Autowired
  private ValueSetController controller;

  @Given("I search for concepts for value set {string}")
  public void search_for_concepts(String valueSet) {
    try {
      // expects predifined data in the test db
      conceptHolder.setConcepts(controller.findConceptsByCodeSetName(valueSet));
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("I find concepts for value set {string}")
  public void i_find_concepts(String valueSet) {
    List<Concept> concepts = conceptHolder.getConcepts();
    assertFalse(concepts.isEmpty());
    concepts.forEach(c -> assertEquals(valueSet, c.codeSetName()));
  }

  @Given("I search for race concepts for value set {string}")
  public void search_for_race_concepts(String valueSet) {
    try {
      // expects predifined data in the test db
      conceptHolder.setRaceConcepts(controller.findRaceConceptsByCodeSetName(valueSet));
    } catch (AccessDeniedException e) {
      exceptionHolder.setException(e);
    } catch (AuthenticationCredentialsNotFoundException e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("I find race concepts for value set {string}")
  public void i_find_race_concepts(String valueSet) {
    List<RaceConcept> concepts = conceptHolder.getRaceConcepts();
    assertFalse(concepts.isEmpty());
    concepts.forEach(c -> assertEquals(valueSet, c.codeSetName()));
  }

}
