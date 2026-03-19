package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileReturningRedirectionSteps {

  private final Active<PatientIdentifier> activePatient;

  private final PatientProfileRedirectRequester requester;

  private final Active<ResultActions> activeResponse;

  PatientProfileReturningRedirectionSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileRedirectRequester requester,
      final Active<ResultActions> activeResponse) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.activeResponse = activeResponse;
  }

  @When("Returning to a Patient Profile")
  public void returning_to_a_patient_profile() {
    activePatient
        .maybeActive()
        .map(patient -> requester.returningTo(patient, "summary"))
        .ifPresent(activeResponse::active);
  }

  @When("Returning to a Patient Profile {patientProfileTab} tab")
  public void returning_to_a_patient_profile_tab(final String tab) {
    activePatient
        .maybeActive()
        .map(patient -> requester.returningTo(patient, tab))
        .ifPresent(activeResponse::active);
  }
}
