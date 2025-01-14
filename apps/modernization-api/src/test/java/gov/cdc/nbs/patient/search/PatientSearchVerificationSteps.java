package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class PatientSearchVerificationSteps {

  private final Active<ResultActions> results;
  private final Active<PatientIdentifier> patient;

  PatientSearchVerificationSteps(
      final Active<ResultActions> results,
      final Active<PatientIdentifier> patient) {
    this.results = results;
    this.patient = patient;
  }

  @Then("the patient search results contain(s) the Patient ID")
  public void the_patient_search_results_contain_patient_id() throws Exception {
    this.results.active()
        .andExpect(
            jsonPath("$.data.findPatientsByFilter.content[*].shortId")
                .value(hasItem((int) this.patient.active().shortId())));
  }

  @Then("I am not able to execute the search")
  public void i_am_not_able_to_execute_the_search() throws Exception {
    this.results.active()
        .andExpect(jsonPath("$.errors[*].message")
            .value(
                hasItem(
                    "User does not have permission to search for Inactive Patients")));
  }

  @Then("search result {int} has a(n) {string} of {string}")
  public void search_result_n_has_a_x_of_y(
      final int position,
      final String field,
      final String value) throws Exception {

    int index = position - 1;

    JsonPathResultMatchers pathMatcher = matchingPath(field, String.valueOf(index));

    String finalValue = null;

    if(!value.isEmpty()) {
      finalValue = value;
    }

    this.results.active()
        .andExpect(pathMatcher.value(matchingValue(field, finalValue)));
  }

  @Then("the search results have a patient with a(n) {string} equal to {string}")
  public void search_results_have_a_patient_with_a(final String field, final String value) throws Exception {

    JsonPathResultMatchers pathMatcher = matchingPath(field, "*");

    this.results.active()
        .andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  @Then("the search results have a patient without a(n) {string} equal to {string}")
  public void search_results_have_a_patient_without_a(final String field, final String value) throws Exception {

    JsonPathResultMatchers pathMatcher = matchingPath(field, "*");

    this.results.active()
        .andExpect(pathMatcher.value(not(matchingValue(field, value))));

  }

  @Then("the search results have a patient without a(n) {string}")
  public void search_results_have_a_patient_without_a(final String field) throws Exception {

    JsonPathResultMatchers pathMatcher = matchingPath(field, "*");

    this.results.active()
        .andExpect(pathMatcher.isEmpty());

  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "status" -> jsonPath("$.data.findPatientsByFilter.content[%s].status", position);
      case "birthday" -> jsonPath("$.data.findPatientsByFilter.content[%s].birthday", position);
      case "address" -> jsonPath("$.data.findPatientsByFilter.content[%s].addresses[*].address", position);
      case "address type" -> jsonPath("$.data.findPatientsByFilter.content[%s].addresses[*].type", position);
      case "address use" -> jsonPath("$.data.findPatientsByFilter.content[%s].addresses[*].use", position);
      case "city" -> jsonPath("$.data.findPatientsByFilter.content[%s].addresses[*].city", position);
      case "state" -> jsonPath("$.data.findPatientsByFilter.content[%s].addresses[*].state", position);
      case "county" -> jsonPath("$.data.findPatientsByFilter.content[%s].addresses[*].county", position);
      case "country" -> jsonPath("$.data.findPatientsByFilter.content[%s].addresses[*].country", position);
      case "zip" -> jsonPath("$.data.findPatientsByFilter.content[%s].addresses[*].zipcode", position);
      case "gender", "sex" -> jsonPath("$.data.findPatientsByFilter.content[%s].gender", position);
      case "first name" -> jsonPath("$.data.findPatientsByFilter.content[%s].names[*].first", position);
      case "last name" -> jsonPath("$.data.findPatientsByFilter.content[%s].names[*].last", position);
      case "middle name" -> jsonPath("$.data.findPatientsByFilter.content[%s].names[*].middle", position);
      case "suffix" -> jsonPath("$.data.findPatientsByFilter.content[%s].names[*].suffix", position);
      case "legal first name" -> jsonPath("$.data.findPatientsByFilter.content[%s].legalName.first", position);
      case "legal middle name" -> jsonPath("$.data.findPatientsByFilter.content[%s].legalName.middle", position);
      case "legal last name" -> jsonPath("$.data.findPatientsByFilter.content[%s].legalName.last", position);
      case "legal name suffix" -> jsonPath("$.data.findPatientsByFilter.content[%s].legalName.suffix", position);
      case "phone number" -> jsonPath("$.data.findPatientsByFilter.content[%s].phones[*]", position);
      case "email", "email address" -> jsonPath(
          "$.data.findPatientsByFilter.content[%s].emails[*]",
          position);
      case "identification type" -> jsonPath(
          "$.data.findPatientsByFilter.content[%s].identification[*].type",
          position);
      case "identification value" -> jsonPath(
          "$.data.findPatientsByFilter.content[%s].identification[*].value",
          position);
      case "patientid", "patient id", "short id" -> jsonPath("$.data.findPatientsByFilter.content[%s].shortId",
          position);
      case "local id" -> jsonPath("$.data.findPatientsByFilter.content[%s].localId", position);
      default -> throw new AssertionError("Unexpected property check %s".formatted(field));
    };
  }

  private Matcher<?> matchingValue(final String field, final String value) {
    return switch (field.toLowerCase()) {
      case "birthday", "sex" -> equalTo(value);
      case "patientid", "patient id", "short id" -> equalTo(Integer.parseInt(value));
      case "status" -> hasItem(PatientStatusCriteriaResolver.resolve(value).name());
      default -> hasItem(value);
    };
  }

  @Then("the search results have a patient with a(n) {string} {} name of {string}")
  public void search_result_n_has_a_x_of_y(
      final String type,
      final String field,
      final String value) throws Exception {

    JsonPathResultMatchers pathMatcher = switch (field.toLowerCase()) {
      case "first" -> jsonPath("$.data.findPatientsByFilter.content[*].names[?(@.type=='%s')].first", type);
      case "last" -> jsonPath("$.data.findPatientsByFilter.content[*].names[?(@.type=='%s')].last", type);
      default -> throw new IllegalStateException("Unexpected value: " + field);
    };

    this.results.active()
        .andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  @Then("the search results have a patient with a(n) {} - {} number of {string}")
  public void search_results_have_a_patient_with_the_phone(final String type, final String use, final String number)
      throws Exception {

    this.results.active()
        .andExpect(
            jsonPath("$.data.findPatientsByFilter.content[*].detailedPhones[?(@.type=='%s' && @.use=='%s')].number",
                type, use)
                .value(number)
        );
  }

  @Then("the patient is in the search result(s)")
  public void the_patient_is_in_the_search_results() throws Exception {
    this.results.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientsByFilter.content[?(@.patient=='%s')]",
                String.valueOf(this.patient.active().id()))
                .exists());
  }

  @Then("the patient is not in the search result(s)")
  public void the_patient_is_not_in_the_search_results() throws Exception {
    this.results.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientsByFilter.content[?(@.patient=='%s')]",
                String.valueOf(this.patient.active().id()))
                .doesNotExist());
  }

  @Then("there is only one patient search result")
  public void there_is_only_one_patient_search_result() throws Exception {
    this.results.active()
        .andExpect(jsonPath("$.data.findPatientsByFilter.total").value(1));
  }

  @Then("there are {int} patient search results")
  public void there_is_are_x_patient_search_result(final int total) throws Exception {
    this.results.active()
        .andExpect(jsonPath("$.data.findPatientsByFilter.total").value(total));
  }

  @Then("the Patient Search Results are not accessible")
  public void the_patient_search_results_are_not_accessible() throws Exception {
    this.results.active().andExpect(accessDenied());
  }
}
