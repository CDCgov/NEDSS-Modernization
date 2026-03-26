package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.LocalDate;

public class PatientPhoneDemographicSteps {

  private final PatientPhoneDemographicApplier applier;
  private final Active<PatientIdentifier> patient;

  PatientPhoneDemographicSteps(
      final PatientPhoneDemographicApplier applier, final Active<PatientIdentifier> patient) {
    this.applier = applier;
    this.patient = patient;
  }

  @Given("the patient has a phone")
  public void homePhone() {
    patient.maybeActive().ifPresent(applier::withPhone);
  }

  @Given("the patient has the phone number {string}-{string} x{string}")
  public void homePhone(final String countryCode, final String number, final String extension) {

    this.patient
        .maybeActive()
        .ifPresent(
            found ->
                applier.withPhone(
                    found, "PH", "H", countryCode, number, extension, RandomUtil.dateInPast()));
  }

  @Given("the patient has the {phoneType} - {phoneUse} number of {string}")
  public void phone(final String type, final String use, final String number) {
    this.patient
        .maybeActive()
        .ifPresent(
            found ->
                applier.withPhone(found, type, use, null, number, null, RandomUtil.dateInPast()));
  }

  @Given("the patient has the {phoneType} - {phoneUse} number of {string} as of {localDate}")
  public void phone(final String type, final String use, final String number, LocalDate asOf) {
    this.patient
        .maybeActive()
        .ifPresent(found -> applier.withPhone(found, type, use, null, number, null, asOf));
  }

  @Given(
      "the patient has the {phoneType} - {phoneUse} phone number of {string} {string} - {string} as of {localDate}")
  public void phone(
      final String type,
      final String use,
      final String countryCode,
      final String phone,
      final String extension,
      final LocalDate localDate) {
    this.patient
        .maybeActive()
        .ifPresent(
            identifier ->
                this.applier.withPhone(
                    identifier, type, use, countryCode, phone, extension, localDate));
  }
}
