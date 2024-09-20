package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileCreateEthnicitySteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileEthnicityRequester requester;
  private final Active<ResultActions> response;


  PatientProfileCreateEthnicitySteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileEthnicityRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient Profile Ethnicity")
  public void i_view_the_patient_profile_ethnicity() {
    this.activePatient.maybeActive()
        .map(requester::ethnicity)
        .ifPresent(this.response::active);
  }

}
