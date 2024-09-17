package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileNameSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileNamesRequester requester;
  private final Active<ResultActions> response;


  PatientProfileNameSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileNamesRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient Profile Names")
  public void i_view_the_patient_profile_names() {
    this.activePatient.maybeActive()
        .map(requester::names)
        .ifPresent(this.response::active);
  }

}
