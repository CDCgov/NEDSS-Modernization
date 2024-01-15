package gov.cdc.nbs.patient.profile.race.validate.category;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileRaceCategoryValidationSteps {

  private final Active<PatientIdentifier> patient;
  private final PatientProfileRaceCategoryValidationRequester requester;
  private final Active<ResultActions> response;

  PatientProfileRaceCategoryValidationSteps(
      final Active<PatientIdentifier> patient,
      final PatientProfileRaceCategoryValidationRequester requester,
      final Active<ResultActions> response
  ) {
    this.patient = patient;
    this.requester = requester;
    this.response = response;
  }

  @When("I check if the patient profile can include the race category {raceCategory}")
  public void i_check_if_the_patient_profile_can_include_the_race_category(final String category) {
    this.patient.maybeActive().map(active -> this.requester.validate(active, category))
        .ifPresent(this.response::active);
  }

  @Then("I am able to include the race category on the patient profile")
  public void i_am_able_to_include_the_race_category_on_the_patient_profile() throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.identifier").doesNotExist());
  }

  @Then("I am not able to include the race category {raceCategory} on the patient profile")
  public void i_am_not_able_to_include_the_race_category_on_the_patient_profile(final String category) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.identifier", equalTo(category)));
  }
}
