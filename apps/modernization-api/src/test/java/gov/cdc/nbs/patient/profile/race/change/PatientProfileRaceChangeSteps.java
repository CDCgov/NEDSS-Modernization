package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.RaceMother;
import gov.cdc.nbs.support.TestAvailable;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileRaceChangeSteps {

    @Autowired
    TestAvailable<PatientIdentifier> patients;

    @Autowired
    PatientRaceChangeController controller;

    @Autowired
    EntityManager entityManager;

    private RaceInput input;

    private DeletePatientRace delete;

    @Before("@patient-profile-race-change")
    public void reset() {
        input = null;
        delete = null;
    }

    @When("a patient's race is added")
    public void a_patient_race_is_added() {
        long patient = patients.one().id();

        input = new RaceInput();
        input.setPatient(patient);
        input.setAsOf(RandomUtil.getRandomDateInPast());
        input.setCategory(RandomUtil.getRandomFromArray(RaceMother.RACE_LIST));

        controller.add(input);
    }

    @Then("the patient has the expected race")
    @Transactional
    public void the_patient_has_the_expected_race() {
        PatientIdentifier patient = this.patients.one();

        Person actual = this.entityManager.find(Person.class, patient.id());

        assertThat(actual.getRaces())
            .anySatisfy(
                race -> assertThat(race)
                    .returns(input.getAsOf(), PersonRace::getAsOfDate)
                    .returns(input.getCategory(), PersonRace::getRaceCategoryCd)
                    .returns(input.getCategory(), PersonRace::getRaceCd)
            )
        ;
    }

    @When("a patient's race is changed")
    public void a_patient_race_is_changed() {
        PatientIdentifier patient = patients.one();

        input = new RaceInput();
        input.setPatient(patient.id());
        input.setAsOf(RandomUtil.getRandomDateInPast());
        input.setCategory(RandomUtil.getRandomFromArray(RaceMother.RACE_LIST));

        controller.update(input);
    }

    @When("a patient's race is removed")
    @Transactional
    public void a_patient_race_is_removed() {

        Person patient = this.entityManager.find(Person.class, patients.one().id());

        this.delete = patient.getRaces()
            .stream()
            .findFirst()
            .map(race -> new DeletePatientRace(patient.getId(), patient.getRaceCategoryCd()))
            .orElseThrow();

        this.controller.delete(this.delete);
    }

    @Then("the patient does not have the expected race")
    @Transactional
    public void the_patient_does_not_have_the_expected_race() {
        PatientIdentifier patient = this.patients.one();

        Person actual = this.entityManager.find(Person.class, patient.id());

        assertThat(actual.getRaces())
            .noneSatisfy(
                race -> assertThat(race)
                    .returns(delete.category(), PersonRace::getRaceCategoryCd)
                    .returns(delete.category(), PersonRace::getRaceCd)
            )
        ;
    }

    @Then("I am unable to add a patient's race")
    public void i_am_unable_to_add_a_patient_ethnicity() {
        assertThatThrownBy(() -> controller.add(input))
            .isInstanceOf(AccessDeniedException.class);
    }

    @Then("I am unable to change a patient's race")
    public void i_am_unable_to_change_a_patient_ethnicity() {
        assertThatThrownBy(() -> controller.update(input))
            .isInstanceOf(AccessDeniedException.class);
    }
}
