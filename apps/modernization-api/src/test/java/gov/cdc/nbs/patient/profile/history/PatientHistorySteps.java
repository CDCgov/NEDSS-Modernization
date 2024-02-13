package gov.cdc.nbs.patient.profile.history;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientHistorySteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientHistoryPreviousVersionVerifier verifier;

  PatientHistorySteps(
      final Active<PatientIdentifier> activePatient,
      final PatientHistoryPreviousVersionVerifier verifier
  ) {
    this.activePatient = activePatient;
    this.verifier = verifier;
  }

  @Then("the patient history contains the previous version")
  public void the_patient_history_contains_the_previous_version() {
    boolean verified = this.activePatient.maybeActive()
        .map(active -> this.verifier.verify(active.id()))
        .orElse(false);

    assertThat(verified).isTrue();

  }
}
