package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.demographic.GeneralInformation;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class PatientProfileGeneralSteps {

  private final Active<PatientInput> input;
  private final Active<Person> patient;

  private final Active<PatientIdentifier> activePatient;
  private final Active<ResultActions> response;
  private final PatientGeneralRequester requester;


  PatientProfileGeneralSteps(
      final Active<PatientInput> input,
      final Active<Person> patient,
      final Active<PatientIdentifier> activePatient,
      final Active<ResultActions> response,
      final PatientGeneralRequester requester
  ) {
    this.input = input;
    this.patient = patient;
    this.activePatient = activePatient;
    this.response = response;
    this.requester = requester;
  }

  @Given("the new patient's marital status is entered")
  public void the_new_patient_marital_status_is_entered() {
    PatientInput active = this.input.active();

    active.setAsOf(RandomUtil.getRandomDateInPast());
    active.setMaritalStatus(RandomUtil.getRandomString());
  }

  @Then("the new patient has the entered marital status")
  public void the_new_patient_has_the_entered_martial_status() {
    Person actual = patient.active();
    PatientInput expected = this.input.active();

    assertThat(actual)
        .extracting(Person::getGeneralInformation)
        .returns(expected.getMaritalStatus(), GeneralInformation::maritalStatus);
  }

  @When("I view the patient's general information")
  public void i_view_the_patient_profile_general_information() {
    this.activePatient.maybeActive()
        .map(this.requester::general)
        .ifPresent(this.response::active);
  }

  @Then("the patient's general information is as of {date}")
  public void the_patients_general_information_includes_the_as_of(final Instant value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.asOf", equalTo(value.toString()))
        );
  }

  @Then("the patient's general information includes the marital status {maritalStatus}")
  public void the_patients_general_information_includes_the_marital_status(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.maritalStatus.id", equalTo(value))
        );
  }

  @Then("the patient's general information includes the occupation {occupation}")
  public void the_patients_general_information_includes_the_occupation(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.occupation.id", equalTo(value))
        );
  }

  @Then("the patient's general information includes an education level of {educationLevel}")
  public void the_patients_general_information_includes_the_education_level(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.educationLevel.id", equalTo(value))
        );
  }

  @Then("the patient's general information includes a primary language of {language}")
  public void the_patients_general_information_includes_the_primary_language(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.primaryLanguage.id", equalTo(value))
        );
  }

  @Then("the patient's general information includes that the patient {indicator} speak English")
  public void the_patients_general_information_includes_speaks_english(final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath("$.data.findPatientProfile.general.speaksEnglish.id", equalTo(value))
        );
  }

  @Then("the patient's general information includes a(n) {string} of {string}")
  public void the_patients_general_information_includes(final String field, final String value) throws Exception {
    JsonPathResultMatchers pathMatcher = matchingPath(field);

    this.response.active()
        .andExpect(pathMatcher.value(equalTo(value)));
  }

  @Then("the patient's general information includes {int} {string}")
  public void the_patients_general_information_includes(final int value, final String field) throws Exception {
    JsonPathResultMatchers pathMatcher = matchingPath(field);

    this.response.active()
        .andDo(print())
        .andExpect(pathMatcher.value(equalTo(value)));
  }

  @Then("the patient's general information does not include state HIV case due to {string}")
  public void the_patients_general_information_does_not_include_state_HIV_case(final String reason) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.general.stateHIVCase.reason", equalToIgnoringCase(reason)));
  }

  private JsonPathResultMatchers matchingPath(final String field) {
    return switch (field.toLowerCase()) {
      case "mother's maiden name" -> jsonPath("$.data.findPatientProfile.general.maternalMaidenName");
      case "adults in the house" -> jsonPath("$.data.findPatientProfile.general.adultsInHouse");
      case "children in the house" -> jsonPath("$.data.findPatientProfile.general.childrenInHouse");
      case "state hiv case" -> jsonPath("$.data.findPatientProfile.general.stateHIVCase.value");
      default -> throw new AssertionError("Unexpected Patient General Information  property %s".formatted(field));
    };
  }
}
