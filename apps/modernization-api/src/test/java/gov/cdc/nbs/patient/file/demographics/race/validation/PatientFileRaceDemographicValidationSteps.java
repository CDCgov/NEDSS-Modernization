package gov.cdc.nbs.patient.file.demographics.race.validation;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileRaceDemographicValidationSteps {

  private final Active<PatientIdentifier> patient;
  private final PatientFileRaceCategoryValidationRequester requester;
  private final Active<ResultActions> response;

  PatientFileRaceDemographicValidationSteps(
      final Active<PatientIdentifier> patient,
      final PatientFileRaceCategoryValidationRequester requester,
      final Active<ResultActions> response
  ) {
    this.patient = patient;
    this.requester = requester;
    this.response = response;
  }

  @When("I check if the patient race demographics can include {raceCategory}")
  public void check(final String category) {
    this.patient.maybeActive().map(active -> this.requester.validate(active, category))
        .ifPresent(this.response::active);
  }

  @Then("I am able to include the race category as a patient race demographic")
  public void success() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.identifier").doesNotExist());
  }

  @Then("I am not able to include {raceCategory} as a patient race demographic")
  public void failure(final String category) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.identifier", equalTo(category)));
  }
}
