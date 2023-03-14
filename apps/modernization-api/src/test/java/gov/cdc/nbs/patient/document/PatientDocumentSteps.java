package gov.cdc.nbs.patient.document;

import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.patient.TestPatients;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientDocumentSteps {

  @Autowired
  TestPatients patients;

  @Autowired
  PatientDocumentByPatientResolver resolver;

  @Autowired
  TestUniqueIdGenerator idGenerator;

  @Autowired
  TestDocumentCleaner documentCleaner;

  @Autowired
  DocumentMother documentMother;

  @Before
  public void clean() {
    documentCleaner.clean(idGenerator.starting());
  }

  @When("the patient has a Case Report")
  public void the_patient_has_a_Case_Report() {
    this.patients.one().ifPresent(this.documentMother::caseReport);
  }


  @Then("the profile has an associated document")
  public void the_profile_has_an_associated_document() {
    Long patient = patients.one().orElseThrow(() -> new IllegalStateException("No patient exists"));

    List<PatientDocument> actual = this.resolver.find(patient);
    assertThat(actual).isNotEmpty();
  }

  @Then("the profile documents are not returned")
  public void the_profile_documents_are_not_returned() {
    Long patient = patients.one().orElseThrow(() -> new IllegalStateException("No patient exists"));

    assertThatThrownBy(() -> this.resolver.find(patient))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("the profile has no associated document")
  public void the_profile_has_no_associated_document() {
    Long patient = patients.one().orElseThrow(() -> new IllegalStateException("No patient exists"));

    List<PatientDocument> actual = this.resolver.find(patient);
    assertThat(actual).isEmpty();
  }
}
