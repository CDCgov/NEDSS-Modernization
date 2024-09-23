package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.Instant;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileCreateEthnicitySteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileEthnicityRequester requester;
  private final Active<ResultActions> response;


  PatientProfileCreateEthnicitySteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileEthnicityRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient Profile Ethnicity")
  public void i_view_the_patient_profile_ethnicity() {
    this.activePatient.maybeActive()
        .map(requester::ethnicity)
        .ifPresent(this.response::active);
  }

  @Then("the patient profile ethnicity has the as of date {date}")
  public void the_patient_profile_ethnicity_has_an_as_of_date_of(final Instant value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.ethnicity.asOf")
                .value(value.toString()));
  }

  @Then("the patient profile ethnicity has the ethnic group {string}")
  public void the_patient_profile_ethnicity_has_the_ethnic_group(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.ethnicity.ethnicGroup")
                .value(value.toString()));
  }

  @Then("the patient profile ethnicity has the unknown reason {string}")
  public void the_patient_profile_ethnicity_has_the_unknown_reason(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.ethnicity.unknownReason")
                .value(value.toString()));
  }
}
