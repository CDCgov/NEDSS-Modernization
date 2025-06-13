package gov.cdc.nbs.patient.demographics.ethnicity;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class PatientEthnicityDemographicsSteps {

  private final Active<PatientIdentifier> patient;
  private final PatientMother mother;

  PatientEthnicityDemographicsSteps(
      final Active<PatientIdentifier> patient,
      final PatientMother mother
  ) {
    this.patient = patient;
    this.mother = mother;
  }

  @Given("the patient has the {ethnicity} ethnicity as of {localDate}")
  public void ethnicity(final String ethnicity, final LocalDate asOf) {
    patient.maybeActive().ifPresent(current -> mother.withEthnicity(current, ethnicity, asOf));
  }

  @Given("the patient has the ethnicity {ethnicity}")
  public void ethnicity(final String ethnicity) {
    patient.maybeActive().ifPresent(current -> mother.withEthnicity(current, ethnicity));
  }

  @Given("the patient's ethnicity is unknown with the reason being {ethnicityUnknownReason}")
  public void unknown(final String reason) {
    patient.maybeActive().ifPresent(current -> mother.withUnknownEthnicity(current, reason));
  }

  @Given("the patient's ethnicity is unknown with the reason being {ethnicityUnknownReason} as of {localDate}")
  public void unknown(final String reason, final LocalDate asOf) {
    patient.maybeActive().ifPresent(current -> mother.withUnknownEthnicity(current, reason, asOf));
  }

  @Given("the patient ethnicity includes( a Spanish origin of) {ethnicityDetail}")
  public void the_patient_ethnicity_includes(final String detail) {
    patient.maybeActive().ifPresent(current -> mother.withSpecificEthnicity(current, detail));
  }

}
