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

}
