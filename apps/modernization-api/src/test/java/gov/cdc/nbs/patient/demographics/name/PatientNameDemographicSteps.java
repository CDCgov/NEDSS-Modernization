package gov.cdc.nbs.patient.demographics.name;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.LocalDate;

public class PatientNameDemographicSteps {

  private final PatientNameDemographicApplier applier;
  private final Active<PatientIdentifier> patient;

  PatientNameDemographicSteps(
      final PatientNameDemographicApplier applier, final Active<PatientIdentifier> patient) {
    this.applier = applier;
    this.patient = patient;
  }

  @Given("the patient has a name")
  public void legalName() {
    applier.withName(patient.active());
  }

  @Given("the patient has the {nameUse} name {string} {string}")
  public void legalName(final String type, final String first, final String last) {

    applier.withName(
        patient.active(),
        type,
        first.equals("null") ? null : first,
        last.equals("null") ? null : last);
  }

  @Given("the patient has the {nameUse} name {string} {string} as of {localDate}")
  public void name(final String type, final String first, final String last, final LocalDate asOf) {

    applier.withName(patient.active(), asOf, type, first, last);
  }

  @Given(
      "the patient has the {nameUse} name {string} {string} {string}, {nameSuffix} as of {localDate}")
  public void name(
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix,
      final LocalDate asOf) {

    applier.withName(
        patient.active(),
        asOf,
        type,
        first.equals("null") ? null : first,
        middle.equals("null") ? null : middle,
        last.equals("null") ? null : last,
        suffix);
  }
}
