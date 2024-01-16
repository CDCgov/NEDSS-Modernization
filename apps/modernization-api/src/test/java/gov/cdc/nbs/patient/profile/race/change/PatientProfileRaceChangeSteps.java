package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfileRaceChangeSteps {

  private final Available<PatientIdentifier> patients;

  private final PatientRaceChangeController controller;

  private final EntityManager entityManager;

  private final Active<RaceInput> input;

  PatientProfileRaceChangeSteps(
      final Available<PatientIdentifier> patients,
      final PatientRaceChangeController controller,
      final EntityManager entityManager,
      final Active<RaceInput> input
  ) {
    this.patients = patients;
    this.controller = controller;
    this.entityManager = entityManager;
    this.input = input;
  }

  @Before("@patient-profile-race-change")
  public void reset() {
    input.active(new RaceInput());
  }

  @Given("I want to set the patient's race category to {raceCategory}")
  public void i_want_to_set_the_patients_race_category_to(final String category) {
    input.active(current -> current.category(category));
  }

  @Given("I want to set the patient's detailed race to {raceDetail}")
  public void i_want_to_set_the_patients_detailed_race_to(final String detailed) {
    input.active(current -> current.withDetail(detailed));
  }

  @Then("the patient has the expected race")
  @Transactional
  public void the_patient_has_the_expected_race() {
    PatientIdentifier patient = this.patients.one();

    Person actual = this.entityManager.find(Person.class, patient.id());

    RaceInput expected = input.active();

    assertThat(actual.getRaces())
        .anySatisfy(
            race -> assertThat(race)
                .returns(expected.getAsOf(), PersonRace::getAsOfDate)
                .returns(expected.getCategory(), PersonRace::getRaceCategoryCd)
                .returns(expected.getCategory(), PersonRace::getRaceCd)
        )
    ;
  }

  @When("a patient's race is changed")
  @Transactional
  public void a_patient_race_is_changed() {
    PatientIdentifier patient = patients.one();

    PersonRace existing = this.entityManager.find(Person.class, patients.one().id())
        .getRaces()
        .stream()
        .findFirst()
        .orElseThrow();

    input.active(
        current ->
            current.patient(patient.id())
                .asOf(RandomUtil.getRandomDateInPast())
                .category(existing.getRaceCategoryCd())
    );

    input.maybeActive().ifPresent(controller::update);
  }

}
