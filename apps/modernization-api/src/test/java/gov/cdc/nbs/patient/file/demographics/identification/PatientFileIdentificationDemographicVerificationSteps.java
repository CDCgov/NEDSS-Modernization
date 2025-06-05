package gov.cdc.nbs.patient.file.demographics.identification;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileIdentificationDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileIdentificationDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then(
      "the patient file identification demographics includes a(n) {identificationType} of {string} as of {localDate}")
  public void includesIdentification(
      final String type,
      final String value,
      final LocalDate asOf
  ) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.value=='%s' )]",
                asOf, type, value
            ).exists()
        );
  }

  @Then("the patient file identification demographics includes a(n) {identificationType} issued by {assigningAuthority}")
  public void includesIdentification(
      final String type,
      final String issuer
  ) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.[?(@.type.value=='%s' &&  @.issuer.value=='%s' )]",
                type, issuer
            ).exists()
        );
  }

}
