package gov.cdc.nbs.patient.file.delete;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientDeleteSteps {

  private final PatientDeleteRequester requester;
  private final Active<PatientIdentifier> patient;
  private final Active<ResultActions> response;

  PatientDeleteSteps(
      final PatientDeleteRequester requester,
      final Active<PatientIdentifier> patient,
      final Active<ResultActions> response
  ) {
    this.requester = requester;
    this.patient = patient;
    this.response = response;
  }

  @When("I delete the patient")
  public void i_delete_the_patient() {
    this.patient.maybeActive()
        .map(PatientIdentifier::id)
        .map(requester::request)
        .ifPresent(response::active);
  }

  @When("I delete an unknown patient")
  public void i_delete_an_unknown_patient() {
    this.response.active(requester.request(Integer.MIN_VALUE));
  }

  @Then("the patient is deleted")
  public void the_patient_is_deleted() throws Exception {
    this.response.active()
        .andExpect(status().isAccepted());
  }

  @Then("the patient is not deleted because of an association with an event")
  public void the_patient_is_not_deleted_because_of_an_association_with_an_event() throws Exception {
    this.response.active()
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.reason")
                .value("Cannot delete patient with Active Revisions")
        );
  }

}
