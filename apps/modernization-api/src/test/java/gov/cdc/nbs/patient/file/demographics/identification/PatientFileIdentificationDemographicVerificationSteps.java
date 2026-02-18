package gov.cdc.nbs.patient.file.demographics.identification;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFileIdentificationDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFileIdentificationDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then(
      "the patient file identification demographics includes a(n) {identificationType} of {string} as of {localDate}")
  public void includes(final String type, final String value, final LocalDate asOf)
      throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.value=='%s' )]",
                    asOf, type, value)
                .exists());
  }

  @Then(
      "the patient file identification demographics includes a(n) {identificationType} issued by {assigningAuthority}")
  public void includes(final String type, final String issuer) throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath("$.[?(@.type.value=='%s' &&  @.issuer.value=='%s' )]", type, issuer).exists());
  }

  @Then(
      "the {nth} identification demographics on the patient file includes a(n) {identificationType} of {string} as of {localDate}")
  public void includesNth(
      final int nth, final String type, final String value, final LocalDate asOf) throws Exception {
    int position = nth - 1;

    this.response
        .active()
        .andExpect(jsonPath("$.[%d].type.value", position).value(type))
        .andExpect(jsonPath("$.[%d].value", position).value(value))
        .andExpect(jsonPath("$.[%d].asOf", position).value(asOf.toString()));
  }

  @Then(
      "the patient file identification demographics does not include an entry with as of {localDate}")
  public void doesNotInclude(final LocalDate value) throws Exception {
    this.response.active().andExpect(jsonPath("$.[?(@.asOf=='%s')]", value).doesNotExist());
  }
}
