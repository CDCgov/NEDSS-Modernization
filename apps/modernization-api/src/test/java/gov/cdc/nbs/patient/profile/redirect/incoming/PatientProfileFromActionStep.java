package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.event.investigation.InvestigationIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileFromActionStep {

  private final Active<InvestigationIdentifier> activeInvestigation;

  private final PatientProfileFromActionRequester requester;

  private final Active<ResultActions> result;

  PatientProfileFromActionStep(
      final Active<InvestigationIdentifier> activeInvestigation,
      final PatientProfileFromActionRequester requester,
      final Active<ResultActions> result
  ) {
    this.activeInvestigation = activeInvestigation;
    this.requester = requester;
    this.result = result;
  }

  @When("navigating to a Patient Profile from an investigation")
  public void navigating_to_a_patient_profile_from_an_investigation() {
    this.activeInvestigation.maybeActive()
        .map(requester::returning)
        .ifPresent(result::active);
  }


}
