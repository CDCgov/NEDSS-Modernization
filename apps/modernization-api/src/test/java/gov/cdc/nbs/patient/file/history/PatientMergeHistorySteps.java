package gov.cdc.nbs.patient.file.history;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

public class PatientMergeHistorySteps {

  private final PatientEntityMother patientMother;
  private final PatientMergeMother patientMergeMother;
  private final Active<ResultActions> response;
  private final PatientMergeHistoryRequester requester;

  public PatientMergeHistorySteps(
      final PatientEntityMother patientMother,
      final PatientMergeMother patientMergeMother,
      final Active<ResultActions> response,
      final PatientMergeHistoryRequester requester
  ) {
    this.patientMother = patientMother;
    this.patientMergeMother = patientMergeMother;
    this.response = response;
    this.requester = requester;
  }


  @Given("patient {string} {string} with id {long} was merged into patient {string} {string} with id {long} by {user}")
  public void patient_was_merged_into_another(
      String supersededFirst, String supersededLast, long supersededId,
      String survivingFirst, String survivingLast, long survivingId,
      ActiveUser user
  ) {

    clean(survivingId, supersededId);

    patientMother.create(supersededId, supersededFirst, supersededLast);
    patientMother.create(survivingId, survivingFirst, survivingLast);

    patientMergeMother.createMerge(
        survivingId, 1, survivingId,
        supersededId, 1, supersededId,
        user.id(),
        LocalDateTime.now()
    );
  }


  @When("I view the merge history for patient {long}")
  public void i_view_the_merge_history_for(final long patient) {
    this.response.active(requester.request(patient));
  }


  @Then("the request succeeds")
  public void the_request_succeeds() throws Exception {
    this.response.active()
        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk());
  }

  void clean(long survivingId, long supersededId) {
    patientMergeMother.remove(survivingId);
    patientMother.remove(survivingId);
    patientMother.remove(supersededId);
  }
}
