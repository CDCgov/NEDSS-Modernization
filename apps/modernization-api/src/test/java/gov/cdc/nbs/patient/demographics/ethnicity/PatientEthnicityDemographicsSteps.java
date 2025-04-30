package gov.cdc.nbs.patient.demographics.ethnicity;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

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

  @Given("the patient has the ethnicity {ethnicity}")
  public void the_patient_has_the_ethnicity(final String ethnicity) {
    patient.maybeActive().ifPresent(current -> mother.withEthnicity(current, ethnicity));
  }

  @Given("the patient has the ethnicity {ethnicity}, specifically {ethnicityDetail}")
  public void the_patient_has_the_ethnicity_specifically(final String ethnicity, final String detail) {
    patient.maybeActive().ifPresent(current -> mother.withSpecificEthnicity(current, ethnicity, detail));
  }

  @Given("the patient ethnicity includes {ethnicityDetail}")
  public void the_patient_ethnicity_includes(final String detail) {
    patient.maybeActive().ifPresent(current -> mother.withSpecificEthnicity(current, detail));
  }

}
