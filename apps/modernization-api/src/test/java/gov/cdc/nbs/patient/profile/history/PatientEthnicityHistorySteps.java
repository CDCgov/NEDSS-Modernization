package gov.cdc.nbs.patient.profile.history;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientEthnicityHistorySteps {
    private final Active<PatientIdentifier> activePatient;
    private final PatientEthnicityPreviousVersionVerifier versionVerifier;

    public PatientEthnicityHistorySteps(Active<PatientIdentifier> activePatient, PatientEthnicityPreviousVersionVerifier versionVerifier) {
        this.activePatient = activePatient;
        this.versionVerifier = versionVerifier;
    }

    @Then("the patient ethnicity history contains the previous version")
    public void the_patient_ethnicity_history_contains_the_previous_version() {
        boolean verified = this.activePatient.maybeActive()
                .map(active -> this.versionVerifier.verify(active.id()))
                .orElse(false);

        assertThat(verified).isTrue();
    }
}
