package gov.cdc.nbs.patient.profile.history;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;

public class PatientNameHistorySteps {
  private final Active<PatientIdentifier> activePatient;
  private final PatientNameHistoryPreviousVersionVerifier versionVerifier;

  public PatientNameHistorySteps(
      Active<PatientIdentifier> activePatient,
      PatientNameHistoryPreviousVersionVerifier versionVerifier) {
    this.activePatient = activePatient;
    this.versionVerifier = versionVerifier;
  }

  @Then("the patient name history contains the previous version")
  public void the_patient_name_history_contains_the_previous_version() {
    boolean verified =
        this.activePatient
            .maybeActive()
            .map(active -> this.versionVerifier.verify(active.id()))
            .orElse(false);

    assertThat(verified).isTrue();
  }
}
