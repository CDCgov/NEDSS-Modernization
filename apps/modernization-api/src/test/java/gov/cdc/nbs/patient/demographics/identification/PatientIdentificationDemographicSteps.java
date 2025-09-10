package gov.cdc.nbs.patient.demographics.identification;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientIdentificationDemographicSteps {

  private final PatientIdentificationDemographicApplier applier;
  private final Active<PatientIdentifier> activePatient;

  PatientIdentificationDemographicSteps(
      final PatientIdentificationDemographicApplier applier,
      final Active<PatientIdentifier> activePatient
  ) {
    this.applier = applier;
    this.activePatient = activePatient;
  }

  @Given("the patient has identification")
  public void identification() {
    applier.withIdentification(this.activePatient.active());
  }

  @Given("the patient can be identified with a(n) {identificationType} of {string}")
  public void identification(
      final String type,
      final String value
  ) {
    applier.withIdentification(
        this.activePatient.active(),
        type,
        value
    );
  }

  @Given("the patient can be identified with a(n) {identificationType} of {string} as of {localDate}")
  public void identification(
      final String type,
      final String value,
      final LocalDate asOf
  ) {
    applier.withIdentification(
        this.activePatient.active(),
        type,
        null,
        value,
        asOf
    );
  }

  @Given("the patient can be identified with a(n) {identificationType} without a value")
  public void identification(final String type) {
    applier.withIdentification(
        this.activePatient.active(),
        type,
        null
    );
  }


}
