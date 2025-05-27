package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.patient.RevisionMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientNamesAContactSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientMother patientMother;
  private final RevisionMother revisionMother;
  private final Active<InvestigationIdentifier> activeInvestigation;
  private final ContactTracingMother mother;
  private final ContactNamedByPatientResolver resolver;

  PatientNamesAContactSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientMother patientMother,
      final RevisionMother revisionMother,
      final Active<InvestigationIdentifier> activeInvestigation,
      final ContactTracingMother mother,
      final ContactNamedByPatientResolver resolver
  ) {
    this.activePatient = activePatient;
    this.patientMother = patientMother;
    this.revisionMother = revisionMother;
    this.activeInvestigation = activeInvestigation;
    this.mother = mother;
    this.resolver = resolver;
  }

  @When("the patient names a contact")
  public void the_patient_names_a_contact() {
    PatientIdentifier revision = revisionMother.revise(activePatient.active());

    long investigation = activeInvestigation.active().identifier();

    PatientIdentifier other = patientMother.available();

    mother.namedByPatient(investigation, revision.id(), other.id());

  }

  @Then("the profile has a contact named by the patient")
  public void the_profile_has_a_contact_named_by_the_patient() {
    Page<PatientContacts.NamedByPatient> actual = resolver.find(
        activePatient.active().id(),
        new GraphQLPage(5));

    assertThat(actual).isNotEmpty();
  }

  @Then("the profile contacts named by the patient are not returned")
  public void the_profile_contacts_named_by_the_patient_are_not_returned() {
    long patient = this.activePatient.active().id();

    GraphQLPage page = new GraphQLPage(5);
    assertThatThrownBy(() -> this.resolver.find(patient, page))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("the profile has no associated contacts named by the patient")
  public void the_profile_has_no_associated_contacts_named_by_the_patient() {
    long patient = this.activePatient.active().id();

    Page<PatientContacts.NamedByPatient> actual = this.resolver.find(patient, new GraphQLPage(5));
    assertThat(actual).isEmpty();
  }
}
