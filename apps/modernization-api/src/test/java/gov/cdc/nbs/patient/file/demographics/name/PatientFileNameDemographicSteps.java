package gov.cdc.nbs.patient.file.demographics.name;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileNameDemographicSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileNameDemographicRequester requester;
  private final Active<ResultActions> response;


  PatientFileNameDemographicSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileNameDemographicRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the patient's name demographics")
  public void i_view_the_patient_names() {
    this.activePatient.maybeActive()
        .map(this.requester::request)
        .ifPresent(this.response::active);
  }


}
