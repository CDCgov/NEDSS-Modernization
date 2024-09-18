package gov.cdc.nbs.patient.profile.administrative;


import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileAdministrativeSteps {

  private final PatientProfileAdministrativeRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientProfileAdministrativeSteps(
      final PatientProfileAdministrativeRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response
  ) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the Patient Profile Administrative")
  public void i_view_the_patient_profile_administration() {
    this.activePatient.maybeActive()
        .map(requester::administrative)
        .ifPresent(this.response::active);
  }



}
