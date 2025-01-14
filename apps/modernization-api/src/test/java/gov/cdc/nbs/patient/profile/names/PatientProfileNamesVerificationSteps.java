package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileNamesVerificationSteps {

  private final Active<ResultActions> response;

  PatientProfileNamesVerificationSteps(final Active<ResultActions> response) {
    this.response = response;
  }

  @Given("the patient profile name as of {date} contains the prefix {namePrefix}")
  public void the_name_as_of_contains_the_prefix(final Instant asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.names.content[?(@.asOf=='%s')].prefix.id", asOf)
                .value(value)
        );
  }

  @Given("the patient profile name as of {date} contains the first name {string}")
  public void the_name_as_of_contains_the_first_name(final Instant asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.names.content[?(@.asOf=='%s')].first", asOf)
                .value(value)
        );
  }

  @Given("the patient profile name as of {date} contains the middle name {string}")
  public void the_name_as_of_contains_the_middle_name(final Instant asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.names.content[?(@.asOf=='%s')].middle", asOf)
                .value(value)
        );
  }

  @Given("the patient profile name as of {date} contains the second middle name {string}")
  public void the_name_as_of_contains_the_second_middle_name(final Instant asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.names.content[?(@.asOf=='%s')].secondMiddle", asOf)
                .value(value)
        );
  }

  @Given("the patient profile name as of {date} contains the last name {string}")
  public void the_name_as_of_contains_the_last_name(final Instant asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.names.content[?(@.asOf=='%s')].last", asOf)
                .value(value)
        );
  }

  @Given("the patient profile name as of {date} contains the second last name {string}")
  public void the_name_as_of_contains_the_second_last_name(final Instant asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.names.content[?(@.asOf=='%s')].secondLast", asOf)
                .value(value)
        );
  }

  @Given("the patient profile name as of {date} contains the suffix {nameSuffix}")
  public void the_name_as_of_contains_the_suffix(final Instant asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.names.content[?(@.asOf=='%s')].suffix.id", asOf)
                .value(value)
        );
  }

  @Given("the patient profile name as of {date} contains the degree {degree}")
  public void the_name_as_of_contains_the_education_level(final Instant asOf, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.names.content[?(@.asOf=='%s')].degree.id", asOf)
                .value(value)
        );
  }

}
