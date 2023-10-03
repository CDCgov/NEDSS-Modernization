package gov.cdc.nbs.patient.profile.summary;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientSummarySteps {

  @Autowired
  TestActive<PatientIdentifier> patient;

  @Autowired
  PatientProfileSummaryRequest request;

  private final TestActive<ResultActions> response = new TestActive<>();

  @Before("@patient-summary")
  public void clear() {
    response.reset();
  }

  @When("I view the Patient Profile Summary")
  @When("a patient summary is requested by patient identifier")
  public void a_patient_summary_is_requested_by_patient_identifier() {
    response.active(
        this.request.summary(patient.active())
    );
  }

  @Then("the Patient Profile Summary is found")
  public void the_summary_is_found() throws Exception {
    this.response.active()
        .andDo(print())
        .andExpect(
            jsonPath("$.data.findPatientProfile.id")
                .value((int) this.patient.active().id())
        )
        .andExpect(
            jsonPath("$.data.findPatientProfile.local")
                .value(this.patient.active().local())
        )
        .andExpect(
            jsonPath("$.data.findPatientProfile.shortId")
                .value((int) this.patient.active().shortId())
        );
  }

  @Then("the Patient Profile Summary is not accessible")
  public void the_summary_is_not_accessible() throws Exception {
    this.response.active().andExpect(accessDenied());
  }

  @Then("the Patient Profile Summary has a(n) {string} of {string}")
  public void the_patient_summary_has_a_value_of(
      final String field,
      final String value
  ) throws Exception {

    JsonPathResultMatchers pathMatcher = resolveForField(field);

    this.response.active()
        .andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  private JsonPathResultMatchers resolveForField(final String field) {
    return switch (field.toLowerCase()) {
      case "race" -> jsonPath("$.data.findPatientProfile.summary.races");
      case "identification type" -> jsonPath("$.data.findPatientProfile.summary.identification[*].type");
      case "identification value" -> jsonPath("$.data.findPatientProfile.summary.identification[*].value");
      default -> throw new AssertionError(String.format("Unexpected property check %s", field));
    };
  }

  private Matcher<?> matchingValue(final String field, final String value) {
    return switch (field.toLowerCase()) {
      case "race", "identification value" -> hasItem(containsStringIgnoringCase(value));
      case "identification type" -> hasItem(value);
      default -> containsStringIgnoringCase(value);
    };
  }

  @Then("the Patient Profile Summary does not have a(n) {string} of {string}")
  public void the_patient_summary_does_not_have_a_value_of(
      final String field,
      final String value
  ) throws Exception {

    JsonPathResultMatchers pathMatcher = resolveForField(field);

    this.response.active()
        .andExpect(pathMatcher.value(Matchers.not(matchingValue(field, value))));
  }

  @Then("the Patient Profile Summary does not contain a(n) {string}")
  public void the_patient_summary_does_not_contain(final String field) throws Exception {
    JsonPathResultMatchers pathMatcher = resolveForField(field);

    this.response.active()
        .andExpect(pathMatcher.isEmpty());
  }

}
