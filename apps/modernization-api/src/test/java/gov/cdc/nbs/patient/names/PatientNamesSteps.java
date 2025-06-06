package gov.cdc.nbs.patient.names;

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
import static org.hamcrest.Matchers.*;

public class PatientNamesSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientNamesRequester requester;
  private final Active<ResultActions> response;
  Exception exception;

  PatientNamesSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientNamesRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I call the patient names api")
  public void i_view_the_patient_names() {
    try {
      this.response.active(
          this.requester.request(activePatient.active().id()));
    } catch (Exception thrown) {
      System.out.println("XXX exception:" + thrown.toString());

      this.exception = thrown;
    }
  }

  @Then("patient names are returned")
  public void names_are_returned() throws Exception {
    this.response.active().andExpect(content().string(not("[]"))).andExpect(content().string(not("")));
  }

  @Then("the {nth} name has a(n) {string} of {string}")
  public void the_nth_name_has_a_x_of_y(
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
      case "asof", "first", "middle", "last", "typevalue", "typename", "suffixvalue", "suffixname", "degreevalue", "degreename" -> equalTo(
          value);
      default -> hasItem(value);
    };
  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "asof" -> jsonPath("$[%s].asOf", position);
      case "first" -> jsonPath("$[%s].first", position);
      case "middle" -> jsonPath("$[%s].middle", position);
      case "last" -> jsonPath("$[%s].last", position);
      case "typename" -> jsonPath("$[%s].type.name", position);
      case "typevalue" -> jsonPath("$[%s].type.value", position);
      case "suffixname" -> jsonPath("$[%s].suffix.name", position);
      case "suffixvalue" -> jsonPath("$[%s].suffix.value", position);
      case "degreename" -> jsonPath("$[%s].degree.name", position);
      case "degreevalue" -> jsonPath("$[%s].degree.value", position);
      default -> throw new AssertionError("Unexpected Patient Name property %s".formatted(field));
    };
  }
}
