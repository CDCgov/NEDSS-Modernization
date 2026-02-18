package gov.cdc.nbs.patient.profile.history;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;

public class PatientRaceHistorySteps {
  private final Active<PatientIdentifier> activePatient;
  private final PatientRaceHistoryPreviousVersionVerifier versionVerifier;

  public PatientRaceHistorySteps(
      final Active<PatientIdentifier> activePatient,
      final PatientRaceHistoryPreviousVersionVerifier versionVerifier) {
    this.activePatient = activePatient;
    this.versionVerifier = versionVerifier;
  }

  @Then("the patient race history contains the previous version")
  public void exists() {
    boolean verified =
        this.activePatient
            .maybeActive()
            .map(active -> this.versionVerifier.verify(active.id()))
            .orElse(false);

    assertThat(verified).isTrue();
  }
}
