package gov.cdc.nbs.patient.file.demographics.administrative;


import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileAdministrativeInformationSteps {

  private final PatientFileAdministrativeInformationRequester requester;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientFileAdministrativeInformationSteps(
      final PatientFileAdministrativeInformationRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response
  ) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's Administrative information")
  public void i_view_the_patient_file_administration() {
    this.activePatient.maybeActive()
        .map(PatientIdentifier::id)
        .map(requester::request)
        .ifPresent(this.response::active);
  }

  @When("I view the Administrative information for patient {int}")
  public void i_view_the_demographics_summary_for(final long patient) {
    this.response.active(requester.request(patient));
  }

}
