package gov.cdc.nbs.patient.profile.gender;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileGenderDemographicSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileGenderRequester requester;
  private final Active<ResultActions> response;

  PatientProfileGenderDemographicSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileGenderRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient Profile Gender demographics")
  public void i_view_the_patient_profile_gender_demographics() {
    this.activePatient.maybeActive()
        .map(requester::gender)
        .ifPresent(this.response::active);
  }
}
