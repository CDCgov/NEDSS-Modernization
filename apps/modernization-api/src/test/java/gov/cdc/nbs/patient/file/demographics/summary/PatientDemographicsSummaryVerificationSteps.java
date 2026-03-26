package gov.cdc.nbs.patient.file.demographics.summary;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

public class PatientDemographicsSummaryVerificationSteps {

  private final Active<ResultActions> results;

  PatientDemographicsSummaryVerificationSteps(final Active<ResultActions> results) {
    this.results = results;
  }

  @Then("the demographics summary of the patient does not include an address")
  public void summary_does_not_have_an_address() throws Exception {
    this.results.active().andExpect(jsonPath("$.address").doesNotExist());
  }

  @Then("the demographics summary of the patient has an address with a use of {string}")
  public void summary_have_an_address_use_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.address.use").value(value));
  }

  @Then("the demographics summary of the patient has an address with a street address of {string}")
  public void summary_have_an_address_street_address_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.address.address").value(value));
  }

  @Then(
      "the demographics summary of the patient has an address with a street address 2 of {string}")
  public void summary_have_an_address_street_address2_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.address.address2").value(value));
  }

  @Then("the demographics summary of the patient has an address with a city of {string}")
  public void summary_have_an_address_city_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.address.city").value(value));
  }

  @Then("the demographics summary of the patient has an address with a state of {string}")
  public void summary_have_an_address_state_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.address.state").value(value));
  }

  @Then("the demographics summary of the patient has an address with a postal code of {postalCode}")
  public void summary_have_an_address_postal_code_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.address.zipcode").value(value));
  }

  @Then("the demographics summary of the patient does not include a phone")
  public void summary_does_not_have_a_phone() throws Exception {
    this.results.active().andExpect(jsonPath("$.address").doesNotExist());
  }

  @Then("the demographics summary of the patient has a phone with a type of {string}")
  public void summary_have_a_phone_type_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.phone.type").value(value));
  }

  @Then("the demographics summary of the patient has a phone with a use of {string}")
  public void summary_have_a_phone_use_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.phone.use").value(value));
  }

  @Then("the demographics summary of the patient has a phone the with number {string}")
  public void summary_have_a_phone_number_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.phone.number").value(value));
  }

  @Then("the demographics summary of the patient has an ethnicity of {string}")
  public void summary_have_an_ethnicity_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.ethnicity", equalToIgnoringCase(value)));
  }

  @Then("the demographics summary of the patient does not include an email address")
  public void summary_does_not_have_an_email_address() throws Exception {
    this.results.active().andExpect(jsonPath("$.email").doesNotExist());
  }

  @Then("the demographics summary of the patient has an email address of {string}")
  public void summary_have_an_email_address_of(final String value) throws Exception {
    this.results.active().andExpect(jsonPath("$.email").value(value));
  }

  @Then("the demographics summary of the patient does not contain any identifications")
  public void summary_does_not_have_any_identifications() throws Exception {
    this.results.active().andExpect(jsonPath("$.identifications").isEmpty());
  }

  @Then(
      "the {nth} identification in the demographics summary of the patient is a(n) {string} of {string}")
  public void summary_have_an_nth_identification_of(
      final int nth, final String type, final String value) throws Exception {
    int position = nth - 1;
    this.results
        .active()
        .andExpect(jsonPath("$.identifications[%d].type", position).value(type))
        .andExpect(jsonPath("$.identifications[%d].value", position).value(value));
  }

  @Then("the demographics summary of the patient does not contain any races")
  public void summary_does_not_have_any_races() throws Exception {
    this.results.active().andExpect(jsonPath("$.races").isEmpty());
  }

  @Then("the {nth} race in the demographics summary of the patient is {string}")
  public void summary_have_an_nth_race_of(final int nth, final String value) throws Exception {
    int position = nth - 1;
    this.results.active().andDo(print()).andExpect(jsonPath("$.races[%d]", position).value(value));
  }
}
