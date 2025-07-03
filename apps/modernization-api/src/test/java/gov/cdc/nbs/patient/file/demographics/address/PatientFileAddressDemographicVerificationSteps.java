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

}
