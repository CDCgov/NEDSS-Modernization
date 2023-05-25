package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.investigation.TestInvestigations;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
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
public class PatientNamesAContactSteps {

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    PatientMother patientMother;

    @Autowired
    TestInvestigations investigations;

    @Autowired
    ContactTracingMother mother;

    @Autowired
    ContactNamedByPatientResolver resolver;

    @Before
    public void clean() {
        mother.reset();
    }

    @When("the patient names a contact")
    public void the_patient_names_a_contact() {
        PatientIdentifier revision = patientMother.revise(patients.one());

        long investigation = investigations.one();

        Person other = patientMother.create();

        mother.namedByPatient(investigation, revision.id(), other.getId());

    }

    @Then("the profile has a contact named by the patient")
    public void the_profile_has_a_contact_named_by_the_patient() {
        Page<PatientContacts.NamedByPatient> actual = resolver.find(
            patients.one().id(),
            new GraphQLPage(5)
        );

        assertThat(actual).isNotEmpty();
    }

    @Then("the profile contacts named by the patient are not returned")
    public void the_profile_contacts_named_by_the_patient_are_not_returned() {
        long patient = this.patients.one().id();

        GraphQLPage page = new GraphQLPage(5);
        assertThatThrownBy(() -> this.resolver.find(patient, page))
            .isInstanceOf(AccessDeniedException.class);
    }

    @Then("the profile has no associated contacts named by the patient")
    public void the_profile_has_no_associated_contacts_named_by_the_patient() {
        long patient = this.patients.one().id();

        Page<PatientContacts.NamedByPatient> actual = this.resolver.find(patient, new GraphQLPage(5));
        assertThat(actual).isEmpty();
    }
}
