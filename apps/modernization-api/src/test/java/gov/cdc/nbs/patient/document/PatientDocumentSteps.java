package gov.cdc.nbs.patient.document;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientDocumentSteps {

  private final GraphQLPage page = new GraphQLPage(1);
  @Autowired
  Authenticated authenticated;

  @Autowired
  Available<PatientIdentifier> patients;

  @Autowired
  PatientMother patientMother;

  @Autowired
  PatientDocumentByPatientResolver resolver;

  @Autowired
  DocumentMother mother;

  @Before("@documents")
  public void clean() {
    mother.reset();
  }

  @When("the patient has a Case Report")
  public void the_patient_has_a_Case_Report() {
    PatientIdentifier revision = patientMother.revise(patients.one());

    this.mother.caseReport(revision.id());
  }

  @When("the patient only has a Case Report with no program area or jurisdiction")
  public void the_patient_only_has_a_report_with_no_program_area_or_jurisdiction() {
    PatientIdentifier revision = patientMother.revise(patients.one());
    this.mother.reset();
    this.mother.caseReportWithoutJurisdiction(revision.id());
  }

  @Then("the profile has an associated document")
  public void the_profile_has_an_associated_document() {
    long patient = this.patients.one().id();

    Page<PatientDocument> actual = resolve(patient);
    assertThat(actual).isNotEmpty();
  }

  private Page<PatientDocument> resolve(long patient) {
    return authenticated.perform(() -> this.resolver.find(patient, page));
  }

  @Then("the profile documents are not returned")
  public void the_profile_documents_are_not_returned() {
    long patient = this.patients.one().id();


    assertThatThrownBy(
        () -> resolve(patient)
    ).isInstanceOf(AccessDeniedException.class);
  }

  @Then("the profile has no associated document")
  public void the_profile_has_no_associated_document() {
    long patient = this.patients.one().id();

    Page<PatientDocument> actual = resolve(patient);
    assertThat(actual).isEmpty();
  }
}
