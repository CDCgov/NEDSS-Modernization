package gov.cdc.nbs.patient.demographics.general;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientGeneralInformationDemographicSteps {

  private final Active<PatientIdentifier> active;
  private final PatientGeneralInformationDemographicApplier applier;

  PatientGeneralInformationDemographicSteps(
      final Active<PatientIdentifier> active,
      final PatientGeneralInformationDemographicApplier applier
  ) {
    this.active = active;
    this.applier = applier;
  }

  @Given("the patient's martial status as of {localDate} is {maritalStatus}")
  public void maritalStatus(final LocalDate asOf, final String value) {
    active.maybeActive().ifPresent(patient -> applier.withMaritalStatus(patient, asOf, value));
  }

  @Given("the patient's martial status is {maritalStatus}")
  public void maritalStatus(final String value) {
    active.maybeActive().ifPresent(patient -> applier.withMaritalStatus(patient, null, value));
  }

  @Given("the patient's maternal maiden name is {string}")
  @Given("the patient's mother's maiden name is {string}")
  public void maternalMaidenName(final String value) {
    active.maybeActive().ifPresent(patient -> applier.withMaidenName(patient, null, value));
  }

  @Given("the patient's maternal maiden name as of {localDate} is {string} ")
  public void maternalMaidenName(final LocalDate asOf, final String value) {
    active.maybeActive().ifPresent(patient -> applier.withMaidenName(patient, asOf, value));
  }

  @Given("the patient lives with {int} adults")
  public void adultsInResidence(final int value) {
    active.maybeActive().ifPresent(patient -> applier.withAdultsInResidence(patient, null, value));
  }

  @Given("the patient lives with {int} children")
  public void childrenInResidence(final int value) {
    active.maybeActive().ifPresent(patient -> applier.withChildrenInResidence(patient, null, value));
  }

  @Given("the patient's primary occupation is {occupation}")
  public void primaryOccupation(final String value) {
    active.maybeActive().ifPresent(patient -> applier.withPrimaryOccupation(patient, null, value));
  }

  @Given("the patient's education level is {educationLevel}")
  public void educationLevel(final String value) {
    active.maybeActive().ifPresent(patient -> applier.withEducationLevel(patient, null, value));
  }

  @Given("the patient's primary language is {language}")
  public void primaryLanguage(final String value) {
    active.maybeActive().ifPresent(patient -> applier.withPrimaryLanguage(patient, null, value));
  }

  @Given("the patient {indicator} speak English")
  public void speaksEnglish(final String value) {
    active.maybeActive().ifPresent(patient -> applier.withSpeaksEnglish(patient, null, value));
  }

  @Given("the patient is associated with state HIV case {string}")
  public void stateHIVCase(final String value) {
    active.maybeActive().ifPresent(patient -> applier.withStateHIVCase(patient, value));
  }

}
