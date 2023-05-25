package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PublicHealthCase;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.investigation.InvestigationMother;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientNamedAsAContactSteps {

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    PatientMother patientMother;

    @Autowired
    InvestigationMother investigationMother;

    @Autowired
    ContactTracingMother mother;

    @Autowired
    PatientNamedByContactResolver resolver;

    @When("the patient is named as a contact")
    public void the_patient_is_named_as_a_contact() {
        PatientIdentifier revision = patientMother.revise(patients.one());

        Person other = patientMother.create();

        PublicHealthCase investigation = investigationMother.investigation(other.getId());

        mother.namedByPatient(
            investigation.getId(),
            other.getId(),
            revision.id()
        );
    }

    @Then("the profile has a contact that named the patient")
    public void the_profile_has_a_contact_that_named_the_patient() {
        Page<PatientContacts.NamedByContact> actual = resolver.find(
            patients.one().id(),
            new GraphQLPage(5)
        );

        assertThat(actual).isNotEmpty();
    }

    @Then("the profile contacts that named the patient are not returned")
    public void the_profile_contacts_that_named_the_patient_are_not_returned() {
        long patient = this.patients.one().id();

        GraphQLPage page = new GraphQLPage(5);

        assertThatThrownBy(() -> this.resolver.find(patient, page))
            .isInstanceOf(AccessDeniedException.class);
    }

    @Then("the profile has no associated contacts that named the patient")
    public void the_profile_has_no_associated_contacts_that_named_the_patient() {
        long patient = this.patients.one().id();

        Page<PatientContacts.NamedByContact> actual = this.resolver.find(patient, new GraphQLPage(5));
        assertThat(actual).isEmpty();
    }
}
