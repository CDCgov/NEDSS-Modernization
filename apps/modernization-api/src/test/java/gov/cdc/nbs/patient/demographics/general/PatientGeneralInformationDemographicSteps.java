package gov.cdc.nbs.patient.demographics.general;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

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

  @Given("the patient is associated with state HIV case {string}")
  public void stateHIVCase(final String value) {
    active.maybeActive().ifPresent(patient -> applier.withStateHIVCase(patient, value));
  }

}
