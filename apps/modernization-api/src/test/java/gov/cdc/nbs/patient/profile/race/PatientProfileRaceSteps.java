package gov.cdc.nbs.patient.profile.race;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.support.Active;
import gov.cdc.nbs.support.Available;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileRaceSteps {

    @Autowired
    PatientMother mother;

    @Autowired
    Active<PatientInput> input;

    @Autowired
    TestPatient patient;

    @Autowired
    Available<PatientIdentifier> patients;

    @Autowired
    PatientRaceResolver resolver;

    @Given("the patient has a race")
    public void the_patient_has_a_race() {
        mother.withRace(patients.one());
    }

    @Given("the new patient's race is entered")
    public void the_new_patient_race_is_entered() {
        PatientInput active = this.input.active();

        active.getRaces().add(RandomUtil.getRandomString());
    }

    @Then("the new patient has the entered race")
    @Transactional
    public void the_new_patient_has_the_entered_race() {
        Person actual = patient.managed();

        if (!actual.getRaces().isEmpty()) {
            assertThat(actual.getRaces())
                .satisfiesExactly(PatientCreateAssertions.containsRaceCategories(this.input.active().getRaces()));
        }
    }

    @Then("the profile has associated races")
    public void the_profile_has_associated_races() {
        long patient = this.patients.one().id();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        GraphQLPage page = new GraphQLPage(1);

        Page<PatientRace> actual = this.resolver.resolve(profile, page);
        assertThat(actual).isNotEmpty();
    }

    @Then("the profile has no associated races")
    public void the_profile_has_no_associated_races() {
        long patient = this.patients.one().id();

        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        GraphQLPage page = new GraphQLPage(1);

        Page<PatientRace> actual = this.resolver.resolve(profile, page);
        assertThat(actual).isEmpty();
    }

    @Then("the profile races are not accessible")
    public void the_profile_race_is_not_accessible() {
        long patient = this.patients.one().id();


        PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

        GraphQLPage page = new GraphQLPage(1);

        assertThatThrownBy(
            () -> this.resolver.resolve(profile, page)
        )
            .isInstanceOf(AccessDeniedException.class);
    }
}
