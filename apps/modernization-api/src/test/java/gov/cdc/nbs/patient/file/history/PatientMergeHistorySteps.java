package gov.cdc.nbs.patient.file.history;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientMergeHistorySteps {

  private final PatientMergeHistoryRequester requester;
  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;

  PatientMergeHistorySteps(
      final PatientMergeHistoryRequester requester,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response
  ) {
    this.requester = requester;
    this.activePatient = activePatient;
    this.response = response;
  }

  @Then("I view the patient's merge history")
  public void i_view_the_patient_file_merge_history() {
    this.activePatient.maybeActive()
        .map(PatientIdentifier::id)
        .map(requester::request)
        .ifPresent(this.response::active);
  }

  @When("I view the merge history for patient {int}")
  public void i_view_the_merge_history_for(final long patient) {
    this.response.active(requester.request(patient));
  }
}
