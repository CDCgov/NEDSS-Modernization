package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.Instant;

public class PatientNameDemographicSteps {

  private final PatientMother mother;
  private final Active<PatientIdentifier> patient;

  PatientNameDemographicSteps(
      final PatientMother mother,
      final Active<PatientIdentifier> patient
  ) {
    this.mother = mother;
    this.patient = patient;
  }

  @Given("the patient has a name")
  public void the_patient_has_a_name() {
    mother.withName(patient.active());
  }

  @Given("the patient has the {nameUse} name {string} {string}")
  public void the_patient_has_the_name(
      final String type,
      final String first,
      final String last
  ) {

    mother.withName(
        patient.active(),
        type,
        first,
        last
    );
  }

  @Given("the patient has the {nameUse} name {string} {string} as of {date}")
  public void the_patient_has_the_name_as_of(
      final String type,
      final String first,
      final String last,
      final Instant asOf
  ) {

    mother.withName(
        patient.active(),
        asOf,
        type,
        first,
        last
    );
  }

  @Given("the patient has the {nameUse} name {string} {string} {string}, {nameSuffix} as of {date}")
  public void the_patient_has_the_name_use_first_middle_last_suffix_as_of(
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix,
      final Instant asOf
  ) {

    mother.withName(
        patient.active(),
        asOf,
        type,
        first,
        middle,
        last,
        suffix
    );

  }

}