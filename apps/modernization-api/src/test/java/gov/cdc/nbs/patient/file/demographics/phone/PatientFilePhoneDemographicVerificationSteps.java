package gov.cdc.nbs.patient.file.demographics.phone;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFilePhoneDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFilePhoneDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then(
      "the patient file phone demographics includes a(n) {phoneType} - {phoneUse} number of {string} as of {localDate}")
  public void includesPhone(
      final String type,
      final String use,
      final String number,
      final LocalDate asOf
  ) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.use.value=='%s' &&  @.phoneNumber=='%s' )]",
                asOf, type, use, number
            ).exists()
        );
  }

  @Then(
      "the patient file phone demographics includes a(n) {phoneType} - {phoneUse} email address of {string} as of {localDate}")
  public void includesEmail(
      final String type,
      final String use,
      final String emailAddress,
      final LocalDate asOf
  ) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.use.value=='%s' &&  @.email=='%s')]",
                asOf, type, use, emailAddress
            ).exists()
        );
  }

}
