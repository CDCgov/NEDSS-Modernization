package gov.cdc.nbs.patient.file.demographics.phone;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import java.time.LocalDate;
import org.springframework.test.web.servlet.ResultActions;

public class PatientFilePhoneDemographicVerificationSteps {

  private final Active<ResultActions> response;

  PatientFilePhoneDemographicVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Then(
      "the patient file phone demographics includes a(n) {phoneType} - {phoneUse} number of {string} as of {localDate}")
  public void includesPhone(
      final String type, final String use, final String number, final LocalDate asOf)
      throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.use.value=='%s' &&  @.phoneNumber=='%s' )]",
                    asOf, type, use, number)
                .exists());
  }

  @Then(
      "the {nth} phones demographics on the patient file includes a(n) {phoneType} - {phoneUse} number of {string} as of {localDate}")
  public void nthPhone(
      final int nth, final String type, final String use, final String number, final LocalDate asOf)
      throws Exception {

    int position = nth - 1;

    this.response
        .active()
        .andExpect(jsonPath("$.[%d].type.value", position).value(type))
        .andExpect(jsonPath("$.[%d].use.value", position).value(use))
        .andExpect(jsonPath("$.[%d].phoneNumber", position).value(number))
        .andExpect(jsonPath("$.[%d].asOf", position).value(asOf.toString()));
  }

  @Then(
      "the patient file phone demographics includes a(n) {phoneType} - {phoneUse} email address of {string} as of {localDate}")
  public void includesEmail(
      final String type, final String use, final String emailAddress, final LocalDate asOf)
      throws Exception {
    this.response
        .active()
        .andExpect(
            jsonPath(
                    "$.[?(@.asOf=='%s' && @.type.value=='%s' &&  @.use.value=='%s' &&  @.email=='%s')]",
                    asOf, type, use, emailAddress)
                .exists());
  }

  @Then(
      "the {nth} phones demographics on the patient file includes a(n) {phoneType} - {phoneUse} email address of {string} as of {localDate}")
  public void nthEmail(
      final int nth,
      final String type,
      final String use,
      final String emailAddress,
      final LocalDate asOf)
      throws Exception {

    int position = nth - 1;

    this.response
        .active()
        .andExpect(jsonPath("$.[%d].type.value", position).value(type))
        .andExpect(jsonPath("$.[%d].use.value", position).value(use))
        .andExpect(jsonPath("$.[%d].email", position).value(emailAddress))
        .andExpect(jsonPath("$.[%d].asOf", position).value(asOf.toString()));
  }

  @Then("the patient file phone demographics does not include an entry with as of {localDate}")
  public void doesNotInclude(final LocalDate value) throws Exception {
    this.response.active().andExpect(jsonPath("$.[?(@.asOf=='%s')]", value).doesNotExist());
  }
}
