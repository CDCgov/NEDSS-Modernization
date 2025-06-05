package gov.cdc.nbs.patient.demographics.identification;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientIdentificationDemographicSteps {

  private final PatientMother mother;
  private final Active<PatientIdentifier> activePatient;

  PatientIdentificationDemographicSteps(
      final PatientMother mother,
      final Active<PatientIdentifier> activePatient
  ) {
    this.mother = mother;
    this.activePatient = activePatient;
  }

  @Given("the patient has identification")
  public void the_patient_has_identification() {
    mother.withIdentification(this.activePatient.active());
  }

  @Given("the patient can be identified with a(n) {identificationType} of {string}")
  public void the_patient_has_identification(
      final String type,
      final String value
  ) {
    mother.withIdentification(
        this.activePatient.active(),
        type,
        value
    );
  }

  @Given("the patient can be identified with a(n) {identificationType} of {string} as of {localDate}")
  public void the_patient_has_identification(
      final String type,
      final String value,
      final LocalDate asOf
  ) {
    mother.withIdentification(
        this.activePatient.active(),
        type,
        value,
        asOf
    );
  }

  @Given("the patient can be identified with a(n) {identificationType} without a value")
  public void the_patient_has_an_invalid_identification(final String type) {
    mother.withIdentification(
        this.activePatient.active(),
        type,
        null
    );
  }


}
