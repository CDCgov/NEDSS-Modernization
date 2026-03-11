package gov.cdc.nbs.patient.profile.history;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;

public class PatientEthnicityHistorySteps {
  private final Active<PatientIdentifier> activePatient;
  private final PatientEthnicityPreviousVersionVerifier verifier;

  PatientEthnicityHistorySteps(
      final Active<PatientIdentifier> activePatient,
      final PatientEthnicityPreviousVersionVerifier verifier) {
    this.activePatient = activePatient;
    this.verifier = verifier;
  }

  @Then("the patient ethnicity history contains the previous version")
  public void recorded() {
    this.activePatient
        .maybeActive()
        .map(active -> this.verifier.verify(active.id()))
        .ifPresent(version -> assertThat(version).isTrue());
  }

  @Then("the patient ethnicity history is not recorded")
  public void notRecorded() {
    this.activePatient
        .maybeActive()
        .map(active -> this.verifier.verify(active.id()))
        .ifPresent(version -> assertThat(version).isFalse());
  }
}
