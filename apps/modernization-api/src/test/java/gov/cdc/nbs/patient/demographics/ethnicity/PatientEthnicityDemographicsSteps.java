package gov.cdc.nbs.patient.demographics.ethnicity;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.LocalDate;

public class PatientEthnicityDemographicsSteps {

  private final Active<PatientIdentifier> patient;
  private final PatientEthnicityDemographicApplier applier;

  PatientEthnicityDemographicsSteps(
      final Active<PatientIdentifier> patient, final PatientEthnicityDemographicApplier applier) {
    this.patient = patient;
    this.applier = applier;
  }

  @Given("the patient has the {ethnicity} ethnicity as of {localDate}")
  public void ethnicity(final String ethnicity, final LocalDate asOf) {
    patient.maybeActive().ifPresent(current -> applier.withEthnicity(current, ethnicity, asOf));
  }

  @Given("the patient has the ethnicity {ethnicity}")
  public void ethnicity(final String ethnicity) {
    ethnicity(ethnicity, LocalDate.now());
  }

  @Given("the patient's ethnicity is unknown with the reason being {ethnicityUnknownReason}")
  public void unknown(final String reason) {
    unknown(reason, LocalDate.now());
  }

  @Given(
      "the patient's ethnicity is unknown with the reason being {ethnicityUnknownReason} as of {localDate}")
  public void unknown(final String reason, final LocalDate asOf) {
    patient.maybeActive().ifPresent(current -> applier.withUnknownEthnicity(current, reason, asOf));
  }

  @Given("the patient ethnicity includes( a Spanish origin of) {ethnicityDetail}")
  public void the_patient_ethnicity_includes(final String detail) {
    patient.maybeActive().ifPresent(current -> applier.withSpecificEthnicity(current, detail));
  }
}
