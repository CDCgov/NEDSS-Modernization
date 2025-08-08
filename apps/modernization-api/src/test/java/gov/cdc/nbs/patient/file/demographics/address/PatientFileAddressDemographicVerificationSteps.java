package gov.cdc.nbs.patient.file.demographics.address;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileAddressDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileAddressDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then(
      "the patient file address demographics includes a(n) {addressType} - {addressUse} address at {string} {string} {string} as of {localDate}")
  public void includesAddress(
      final String type,
      final String use,
      final String address,
      final String city,
      final String zip,
      final LocalDate asOf
  ) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.use.value=='%s' &&  @.address1=='%s' &&  @.city=='%s' &&  @.zipcode=='%s')]",
                asOf, type, use, address, city, zip
            ).exists()
        );
  }

  @Then(
      "the patient file address demographics does not include a(n) {addressType} - {addressUse} address at {string} {string} {string} as of {localDate}")
  public void doesNotIncludeAddress(
      final String type,
      final String use,
      final String address,
      final String city,
      final String zip,
      final LocalDate asOf
  ) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.use.value=='%s' &&  @.address1=='%s' &&  @.city=='%s' &&  @.zipcode=='%s')]",
                asOf, type, use, address, city, zip
            ).doesNotExist()
        );
  }

  @Then(
      "the {nth} address demographics on the patient file includes a(n) {addressType} - {addressUse} address at {string} {string} {string} as of {localDate}")
  public void includesNt(
      final int nth,
      final String type,
      final String use,
      final String address,
      final String city,
      final String zip,
      final LocalDate asOf
  ) throws Exception {

    int position = nth - 1;

    this.response.active()
        .andExpect(jsonPath("$.[%d].type.value", position).value(type))
        .andExpect(jsonPath("$.[%d].use.value", position).value(use))
        .andExpect(jsonPath("$.[%d].address1", position).value(address))
        .andExpect(jsonPath("$.[%d].city", position).value(city))
        .andExpect(jsonPath("$.[%d].zipcode", position).value(zip))
        .andExpect(jsonPath("$.[%d].asOf", position).value(asOf.toString()));
  }

  @Then("the {nth} address demographic on the patient file is as of {localDate}")
  public void asOf(final int nth, final LocalDate value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].asOf", nth - 1).value(value.toString()));
  }

  @Then("the {nth} address demographic on the patient file has the address type {addressType}")
  public void type(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].type.value", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the address use {addressUse}")
  public void use(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].use.value", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the street address {string}")
  public void address(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].address1", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the street address 2 {string}")
  public void address2(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].address2", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the city {string}")
  public void city(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].city", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the state {state}")
  public void state(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].state.value", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the zip(code) {string}")
  public void zip(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].zipcode", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the county {county}")
  public void county(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].county.value", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the country {country}")
  public void country(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].country.value", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the census tract {string}")
  public void censusTract(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].censusTract", nth - 1).value(value));
  }

  @Then("the {nth} address demographic on the patient file has the comment {string}")
  public void comment(final int nth, final String value) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.[%d].comment", nth - 1).value(value));
  }
}
