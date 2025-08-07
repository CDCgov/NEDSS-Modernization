package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientFileSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientFileRequester requester;
  private final Active<ResultActions> response;


  PatientFileSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientFileRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the Patient File")
  public void i_view_the_patient_file_of_the_patient() {
    activePatient.maybeActive()
        .map(this.requester::request)
        .ifPresent(this.response::active);
  }

  @When("I view the Patient File for patient {long}")
  public void i_view_the_patient_file_of_the_patient(final long patient) {
    this.response.active(this.requester.request(patient));
  }

  @Then("the patient file has patient unique identifier")
  public void the_patient_identifier_is_present() throws Exception {
    String identifier = String.valueOf(this.activePatient.active().id());
    this.response.active().andExpect(jsonPath("$.id").value(identifier));
  }

  @Then("the patient file has patient local identifier")
  public void the_patient_local_is_present() throws Exception {
    String local = this.activePatient.active().local();
    this.response.active().andExpect(jsonPath("$.local").value(local));
  }

  @Then("the patient file has a status of {string}")
  public void the_patient_status_is(final String status) throws Exception {
    this.response.active().andExpect(jsonPath("$.status").value(status));
  }

  @Then("the patient file has the patient born on {string}")
  public void the_patient_was_born_on(final String status) throws Exception {
    this.response.active().andExpect(jsonPath("$.birthday").value(status));
  }

  @Then("the patient file has the patient deceased on {string}")
  public void the_patient_is_deceased_on(final String status) throws Exception {
    this.response.active().andExpect(jsonPath("$.deceasedOn").value(status));
  }

  @Then("the patient file does not contain a name")
  public void the_patient_does_not_contain_a_name() throws Exception {
    this.response.active().andExpect(jsonPath("$.name").doesNotExist());
  }

  @Then("the patient file contains the legal name")
  public void the_patient_file_contains_the_legal_name() throws Exception {
    this.response.active().andExpect(jsonPath("$.name.type").value("Legal"));
  }

  @Then("the patient file has a first name of {string}")
  public void the_patient_file_has_a_first_name(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.name.first").value(value));
  }

  @Then("the patient file has a middle name of {string}")
  public void the_patient_file_has_a_middle_name(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.name.middle").value(value));
  }

  @Then("the patient file has a last name of {string}")
  public void the_patient_file_has_a_last_name(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.name.last").value(value));
  }

  @Then("the patient file has a name suffix of {string}")
  public void the_patient_file_has_a_name_suffix(final String value) throws Exception {
    this.response.active().andExpect(jsonPath("$.name.suffix").value(value));
  }

  @Then("the patient file can be deleted")
  public void the_patient_can_be_deleted() throws Exception {
    this.response.active().andExpect(jsonPath("$.deletability").value(PatientDeletability.DELETABLE.value()));
  }

  @ParameterType(name = "patientNotDeletableReason", value = "(?i)(has associations|is inactive)")
  public PatientDeletability patientNotDeletableReason(final String value) {
    return switch (value.toLowerCase()) {
      case "has associations" -> PatientDeletability.HAS_ASSOCIATIONS;
      case "is inactive" -> PatientDeletability.IS_INACTIVE;
      default -> null;
    };
  }

  @Then("the patient file cannot be deleted because the patient {patientNotDeletableReason}")
  public void the_patient_deletability_is(final PatientDeletability deletability) throws Exception {
    this.response.active().andExpect(jsonPath("$.deletability").value(deletability.value()));
  }

  @Then("I am unable to delete the patient because it was not found")
  @Then("I am unable to edit the patient because it was not found")
  public void notFound() throws Exception {
    this.response.active()
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                "$.reason",
                startsWith("Unable to find patient")
            )
        );
  }

  @Then("I am unable to edit the patient")
  public void unableToEdit() throws Exception {
    this.response.active()
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                "$.reason",
                equalTo("Unable to apply changes to %d".formatted(this.activePatient.active().id()))
            )
        );
  }
}
