package gov.cdc.nbs.patient.demographics.race;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientRaceDemographicsSteps {

  private final Active<PatientIdentifier> patient;
  private final PatientRaceDemographicApplier applier;

  PatientRaceDemographicsSteps(
      final Active<PatientIdentifier> patient,
      final PatientRaceDemographicApplier applier
  ) {
    this.patient = patient;
    this.applier = applier;
  }

  @Given("the patient has a race")
  public void exists() {
    category(RandomUtil.oneFrom("2106-3", "2054-5", "2028-9"));
  }

  @Given("the patient has the race category {raceCategory}")
  public void category(final String category) {
    category(category, RandomUtil.dateInPast());
  }

  @Given("the patient's race is {raceCategory} as of {localDate}")
  public void category(final String category, final LocalDate asOf) {
    patient.maybeActive().ifPresent(current -> applier.withRace(current, asOf, category));
  }

  @Given("the patient race of {raceCategory} includes {raceDetail}")
  public void detailed(final String category, final String detail) {
    patient.maybeActive().ifPresent(current -> applier.withRace(current, category, detail));
  }
}
