package gov.cdc.nbs.patient.profile.phone;

import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientProfilePhoneSteps {

  final PatientMother mother;

  final Active<PatientIdentifier> activePatient;

  final Active<PatientInput> input;

  final TestPatient patient;

  PatientProfilePhoneSteps(
      final PatientMother mother,
      final Active<PatientIdentifier> activePatient,
      final Active<PatientInput> input,
      final TestPatient patient
  ) {
    this.mother = mother;
    this.activePatient = activePatient;
    this.input = input;
    this.patient = patient;
  }

  @Given("the patient has a phone")
  public void the_patient_has_a_phone() {
    activePatient.maybeActive().ifPresent(mother::withPhone);

  }

  @Given("the patient has the {phoneType} - {phoneUse} number of {string}")
  public void the_patient_has_the_phone(final String type, final String use, final String number) {
    this.activePatient.maybeActive()
        .ifPresent(found -> mother.withPhone(found, type, use, null, number, null));
  }

  @Given("the patient has the {phoneType} - {phoneUse} number of {string} as of {localDate}")
  public void the_patient_has_the_phone_as_of(final String type, final String use, final String number,
      LocalDate asOf) {
    this.activePatient.maybeActive()
        .ifPresent(found -> mother.withPhone(found, type, use, null, number, null, asOf));
  }
}
