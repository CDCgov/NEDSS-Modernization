package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PatientProfileBirthDemographicSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileBirthRequester requester;
  private final Active<ResultActions> response;

  PatientProfileBirthDemographicSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileBirthRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient Profile Birth demographics")
  public void i_view_the_patient_profile_birth_demographics() {
    this.activePatient.maybeActive()
        .map(requester::birth)
        .ifPresent(this.response::active);
  }
}
