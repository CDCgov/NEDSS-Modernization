package gov.cdc.nbs.patient.investigation;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.hamcrest.Matcher;
import static gov.cdc.nbs.graphql.GraphQLErrorMatchers.accessDenied;
import static org.hamcrest.Matchers.*;

public class PatientInvestigationsSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientInvestigationsRequester requester;
  private final Active<ResultActions> response;
  Exception exception;

  PatientInvestigationsSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientInvestigationsRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I view the patient investigations")
  public void i_view_the_patient_investigations() {
    try {
      this.response.active(
          this.requester.request(activePatient.active().id(), false));
    } catch (Exception thrown) {
      this.exception = thrown;
    }
  }

  @When("I view the open patient investigations")
  public void i_view_the_patient_open_investigations() {
    try {
      this.response.active(
          this.requester.request(activePatient.active().id(), true));
    } catch (Exception thrown) {
      this.exception = thrown;
    }
  }

  @Then("an empty array is returned")
  public void an_empty_array_is_returned() throws Exception {
    this.response.active().andExpect(content().string("[]"));
  }

  @Then("investigations are returned")
  public void investigations_are_returned() throws Exception {
    this.response.active().andExpect(content().string(not("[]")));
  }

  @Then("the {nth} investigation has a(n) {string} of {string}")
  public void the_nth_investigation_has_a_x_of_y(
      final int position,
      final String field,
      final String value) throws Exception {
    int index = position - 1;

    JsonPathResultMatchers pathMatcher = matchingPath(field, String.valueOf(index));

    this.response.active()
        .andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  private Matcher<?> matchingValue(final String field, final String value) {
    return switch (field.toLowerCase()) {
      case "status", "start date" -> equalTo(value);
      default -> hasItem(value);
    };
  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "status" -> jsonPath("$[%s].status", position);
      case "start date" -> jsonPath("$[%s].startedOn", position);
      default -> throw new AssertionError("Unexpected Investigation property %s".formatted(field));
    };
  }

}
