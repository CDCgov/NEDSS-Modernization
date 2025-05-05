package gov.cdc.nbs.patient.demographics.race;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientRaceDemographicsSteps {

  private final Active<PatientIdentifier> patient;
  private final PatientMother mother;

  PatientRaceDemographicsSteps(
      final Active<PatientIdentifier> patient,
      final PatientMother mother
  ) {
    this.patient = patient;
    this.mother = mother;
  }

  @Given("the patient has a race")
  public void the_patient_has_a_race() {
    patient.maybeActive().ifPresent(mother::withRace);
  }

  @Given("the patient has the race category {raceCategory}")
  public void the_patient_has_the_race_category(final String category) {
    patient.maybeActive().ifPresent(current -> mother.withRace(current, category));
  }

  @Given("the patient's race is {raceCategory} as of {localDate}")
  public void the_patient_has_the_race_category_as_of(final String category, final LocalDate asOf) {
    patient.maybeActive().ifPresent(current -> mother.withRace(current, asOf, category));
  }

  @Given("the patient race of {raceCategory} includes {raceDetail}")
  public void the_patient_race_of_category_includes(final String category, final String detail) {
    patient.maybeActive().ifPresent(current -> mother.withRaceIncluding(current, category, detail));
  }
}
