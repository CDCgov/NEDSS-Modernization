package gov.cdc.nbs.patient.demographics.administrative;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientAdministrativeInformationSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientAdministrativeInformationApplier applier;

  PatientAdministrativeInformationSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientAdministrativeInformationApplier applier
  ) {
    this.activePatient = activePatient;
    this.applier = applier;
  }

  @Given("the patient has an administrative as of date of {localDate}")
  public void withAsOfDate(final LocalDate value) {
    this.activePatient.maybeActive().ifPresent(patient -> applier.withAsOf(patient, value));
  }

  @Given("the patient has an administrative comment of {string}")
  public void withComment(final String value) {
    this.activePatient.maybeActive().ifPresent(patient -> applier.withComment(patient, value));
  }
}
