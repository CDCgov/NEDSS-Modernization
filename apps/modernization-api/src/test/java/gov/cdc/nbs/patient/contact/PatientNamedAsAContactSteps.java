package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.event.investigation.InvestigationMother;
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
public class PatientNamedAsAContactSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientMother patientMother;
  private final RevisionMother revisionMother;
  private final InvestigationMother investigationMother;
  private final ContactTracingMother mother;
  private final PatientNamedByContactResolver resolver;

  PatientNamedAsAContactSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientMother patientMother, RevisionMother revisionMother,
      final InvestigationMother investigationMother,
      final ContactTracingMother mother,
      final PatientNamedByContactResolver resolver
  ) {
    this.activePatient = activePatient;
    this.patientMother = patientMother;
    this.revisionMother = revisionMother;
    this.investigationMother = investigationMother;
    this.mother = mother;
    this.resolver = resolver;
  }

  @When("the patient is named as a contact")
  public void the_patient_is_named_as_a_contact() {
    PatientIdentifier revision = revisionMother.revise(activePatient.active());

    PatientIdentifier other = patientMother.available();

    InvestigationIdentifier investigation = investigationMother.investigation(other.id());

    mother.namedByPatient(
        investigation.identifier(),
        other.id(),
        revision.id());
  }

  @Then("the profile has a contact that named the patient")
  public void the_profile_has_a_contact_that_named_the_patient() {
    Page<PatientContacts.NamedByContact> actual = resolver.find(
        activePatient.active().id(),
        new GraphQLPage(5));

    assertThat(actual).isNotEmpty();
  }

  @Then("the profile contacts that named the patient are not returned")
  public void the_profile_contacts_that_named_the_patient_are_not_returned() {
    long patient = this.activePatient.active().id();

    GraphQLPage page = new GraphQLPage(5);

    assertThatThrownBy(() -> this.resolver.find(patient, page))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("the profile has no associated contacts that named the patient")
  public void the_profile_has_no_associated_contacts_that_named_the_patient() {
    long patient = this.activePatient.active().id();

    Page<PatientContacts.NamedByContact> actual = this.resolver.find(patient, new GraphQLPage(5));
    assertThat(actual).isEmpty();
  }
}
