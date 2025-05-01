package gov.cdc.nbs.patient.file.delete;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientDeleteSteps {

  private final GraphQLPatientDeleteRequester requester;
  private final Active<PatientIdentifier> patient;
  private final Active<ResultActions> response;

  PatientDeleteSteps(
      final GraphQLPatientDeleteRequester requester,
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
        .andExpect(jsonPath("$.data.deletePatient.__typename")
            .value("PatientDeleteSuccessful")
        );
  }

  @Then("there is no patient to delete")
  public void there_is_no_patient_to_delete() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.deletePatient.reason",
                startsWith("Unable to find patient")
            )
        );
  }

  @Then("the patient is not deleted because of an association with an event")
  public void the_patient_is_not_deleted_because_of_an_association_with_an_event() throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.deletePatient.reason")
                .value("Cannot delete patient with Active Revisions")
        );
  }

}
