package gov.cdc.nbs.patient.file.demographics.summary;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientDemographicsSummarySteps {

  private final Active<PatientIdentifier> patient;
  private final Active<ResultActions> response;
  private final PatientDemographicsSummaryRequester requester;

  PatientDemographicsSummarySteps(
      final Active<PatientIdentifier> patient,
      final Active<ResultActions> response,
      final PatientDemographicsSummaryRequester requester) {
    this.patient = patient;
    this.response = response;
    this.requester = requester;
  }

  @When("I view the demographics summary of the patient")
  public void i_view_the_demographics_summary_of_the_patient() {
    patient
        .maybeActive()
        .map(PatientIdentifier::id)
        .map(requester::request)
        .ifPresent(response::active);
  }

  @When("I view the demographics summary for patient {int}")
  public void i_view_the_demographics_summary_for(final long patient) {
    this.response.active(requester.request(patient));
  }
}
