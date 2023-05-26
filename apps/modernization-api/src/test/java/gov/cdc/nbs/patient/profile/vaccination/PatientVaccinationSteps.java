package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.graphql.GraphQLPage;
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
public class PatientVaccinationSteps {

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    PatientVaccinationResolver resolver;

    @Autowired
    VaccinationMother mother;

    @Autowired
    PatientMother patientMother;

    @Before
    public void clean() {
        mother.reset();
    }

    @When("the patient is vaccinated")
    public void the_patient_has_a_Case_Report() {
        PatientIdentifier revision = patientMother.revise(patients.one());

        this.mother.vaccinate(revision.id());
    }

    @Then("the profile has an associated vaccination")
    public void the_profile_has_an_associated_vaccination() {
        long patient = this.patients.one().id();

        Page<PatientVaccination> actual = this.resolver.find(patient, new GraphQLPage(1));
        assertThat(actual).isNotEmpty();
    }

    @Then("the profile has no associated vaccination")
    public void the_profile_has_no_associated_vaccination() {
        long patient = this.patients.one().id();

        Page<PatientVaccination> actual = this.resolver.find(patient, new GraphQLPage(1));
        assertThat(actual).isEmpty();
    }

    @Then("the profile vaccinations are not accessible")
    public void the_profile_vaccinations_are_not_accessible() {
        long patient = this.patients.one().id();

        GraphQLPage page = new GraphQLPage(1);

        assertThatThrownBy(
            () -> this.resolver.find(
                patient,
                page
            )
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
