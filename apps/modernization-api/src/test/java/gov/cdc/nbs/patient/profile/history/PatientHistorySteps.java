package gov.cdc.nbs.patient.profile.history;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
    this.activePatient.maybeActive()
        .flatMap(active -> this.verifier.verify(active.id()))
        .ifPresentOrElse(
            found -> assertThat(found.previous())
                .as("Patient history contains the previous version")
                .isEqualByComparingTo((short) (found.version() - 1)),
            () -> fail("No patient history found")
        );
  }
}
