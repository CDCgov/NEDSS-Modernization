package gov.cdc.nbs.patient.morbidity;

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
public class PatientMorbidityReportSteps {

    @Autowired
    TestPatientIdentifier patients;

    @Autowired
    PatientMother patientMother;

    @Autowired
    PatientMorbidityResolver resolver;

    @Autowired
    MorbidityReportMother mother;

    @Before
    public void clean() {
        mother.reset();
    }

    @When("the patient has a Morbidity Report")
    public void the_patient_has_a_morbidity_report() {
        PatientIdentifier revision = patientMother.revise(patients.one());

        mother.morbidityReport(revision.id());
    }

    @Then("the profile has an associated morbidity report")
    public void the_profile_has_an_associated_morbidity_report() {
        long patient = this.patients.one().id();

        Page<PatientMorbidity> actual = this.resolver.find(patient, new GraphQLPage(5));
        assertThat(actual).isNotEmpty();
    }


    @Then("the profile has no associated morbidity report")
    public void the_profile_has_no_associated_morbidity_report() {
        long patient = this.patients.one().id();

        Page<PatientMorbidity> actual = this.resolver.find(patient, new GraphQLPage(5));
        assertThat(actual).isEmpty();
    }


    @Then("the profile morbidity reports are not returned")
    public void the_profile_morbidity_reports_are_not_returned() {
        long patient = this.patients.one().id();

        GraphQLPage page = new GraphQLPage(5);

        assertThatThrownBy(() -> {
            this.resolver.find(patient, page);
        })
            .isInstanceOf(AccessDeniedException.class);
    }
}
