package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileRedirectionSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileRedirectRequester requester;
  private final Active<ResultActions> response;

  PatientProfileRedirectionSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileRedirectRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("navigating to a Patient Profile from event search results")
  public void navigating_to_a_patient_profile_from_event_search_results() {
    this.activePatient.maybeActive()
        .map(requester::profile)
        .ifPresent(response::active);
  }
}
