package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;


public class PatientProfileGeneralSteps {

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;
  private final PatientGeneralRequester requester;


  PatientProfileGeneralSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response,
      final PatientGeneralRequester requester
  ) {
    this.activePatient = activePatient;
    this.response = response;
    this.requester = requester;
  }

  @When("I view the Patient Profile General Information")
  @When("I view the patient's general information")
  public void i_view_the_patient_profile_general_information() {
    this.activePatient.maybeActive()
        .map(this.requester::general)
        .ifPresent(this.response::active);
  }

}
