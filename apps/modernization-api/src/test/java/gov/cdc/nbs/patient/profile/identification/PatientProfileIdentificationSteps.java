package gov.cdc.nbs.patient.profile.identification;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileIdentificationSteps {

  private final Active<PatientIdentifier> activePatient;
  private final Active<Pageable> activePageable;
  private final PatientProfileIdentificationRequester requester;
  private final Active<ResultActions> response;

  PatientProfileIdentificationSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<Pageable> activePageable,
      final PatientProfileIdentificationRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.activePageable = activePageable;
    this.requester = requester;
    this.response = response;
  }

  @Given("I view the Patient Profile Identification")
  public void i_view_the_patient_profile_identifications() {
    this.activePatient.maybeActive()
        .map(this::viewInvestigations)
        .ifPresent(this.response::active);
  }

  private ResultActions viewInvestigations(final PatientIdentifier patient) {
    return
        this.activePageable.maybeActive().isPresent()
            ? this.requester.identifications(patient, this.activePageable.active())
            : this.requester.identifications(patient);
  }

  @Then("the profile has no associated identifications")
  public void the_profile_has_no_associated_identifications() throws Exception {
    checkIdentificationTotal(0);
  }

  private void checkIdentificationTotal(int total) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.identification.total")
            .value(equalTo(total)));
  }

  @Then("the patient has {int} identification entries")
  public void the_patient_has_x_identification_entries(int x) throws Exception {
    checkIdentificationTotal(x);
  }

  @Then("the patient's identification includes {int} entries")
  public void the_patients_identification_includes_x_entries(int x) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.identification.content[*]")
            .value(hasSize(x)));
  }

  @Then("the patient's identifications include a(n) {identificationType} of {string}")
  public void the_patients_identification_includes_the_type(final String type, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientProfile.identification.content[?(@.type.id=='%s')].value",
                type
            ).value(hasItem(value))
        );
  }

  @Then("the patient's identifications includes a(n) {identificationType}")
  public void the_patients_identification_includes_the_type(final String type) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientProfile.identification.content[?(@.type.id=='%s')]",
                type
            ).exists()
        );
  }

  @Then("the patient's identifications does not include a(n) {identificationType}")
  public void the_patients_identification_does_not_include_the_type(final String type) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientProfile.identification.content[?(@.type.id=='%s')]",
                type
            ).doesNotExist()
        );
  }
}
