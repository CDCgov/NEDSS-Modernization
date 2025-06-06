package gov.cdc.nbs.patient.investigation;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientFileInvestigationsVerificationSteps {


  private final Active<ResultActions> response;

  PatientFileInvestigationsVerificationSteps(
      final Active<ResultActions> response
  ) {

    this.response = response;
  }

  @Then("the patient file has {int} investigation(s)")
  public void found(final int count) {
    this.response.active().andExpect(json)
  }

  @Then("the {nth} investigation has a(n) {string} of {string}")
  public void the_nth_investigation_has_a_x_of_y(
      final int position,
      final String field,
      final String value
  ) throws Exception {
    int index = position - 1;

    JsonPathResultMatchers pathMatcher = matchingPath(field, String.valueOf(index));

    this.response.active()
        .andExpect(pathMatcher.value(matchingValue(field, value)));
  }

  private Matcher<?> matchingValue(final String field, final String value) {
    return switch (field.toLowerCase()) {
      case "status", "start date", "condition", "jurisdiction" -> equalTo(value);
      default -> hasItem(value);
    };
  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "jurisdiction" -> jsonPath("$[%s].jurisdiction", position);
      case "condition" -> jsonPath("$[%s].condition", position);
      case "status" -> jsonPath("$[%s].status", position);
      case "start date" -> jsonPath("$[%s].startedOn", position);
      default -> throw new AssertionError("Unexpected Investigation property %s".formatted(field));
    };
  }

}
