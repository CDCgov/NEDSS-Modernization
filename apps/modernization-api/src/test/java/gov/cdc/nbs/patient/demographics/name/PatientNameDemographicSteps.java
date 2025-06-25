package gov.cdc.nbs.patient.demographics.name;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

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
        first.equals("null") ? null : first,
        last.equals("null") ? null : last
    );
  }

  @Given("the patient has the {nameUse} name {string} {string} as of {localDate}")
  public void the_patient_has_the_name_as_of(
      final String type,
      final String first,
      final String last,
      final LocalDate asOf
  ) {

    mother.withName(
        patient.active(),
        asOf,
        type,
        first,
        last
    );
  }

  @Given("the patient has the {nameUse} name {string} {string} {string}, {nameSuffix} as of {localDate}")
  public void the_patient_has_the_name_use_first_middle_last_suffix_as_of(
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix,
      final LocalDate asOf
  ) {

    mother.withName(
        patient.active(),
        asOf,
        type,
        first.equals("null") ? null : first,
        middle.equals("null") ? null : middle,
        last.equals("null") ? null : last,
        suffix
    );

  }

}
