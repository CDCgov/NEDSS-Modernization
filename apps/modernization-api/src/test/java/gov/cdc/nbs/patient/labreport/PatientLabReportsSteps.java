package gov.cdc.nbs.patient.labreport;

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

public class PatientLabReportsSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientLabReportRequester requester;
  private final Active<ResultActions> response;
  Exception exception;

  PatientLabReportsSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientLabReportRequester requester,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @When("I call the patient lab reports api")
  public void i_view_the_patient_labreports() {
    try {
      this.response.active(
          this.requester.request(activePatient.active().id()));
    } catch (Exception thrown) {
      System.out.println("XXX exception:" + thrown);

      this.exception = thrown;
    }
  }

  @Then("lab reports are returned")
  public void lab_reports_are_returned() throws Exception {
    this.response.active().andExpect(content().string(not("[]")));
  }

  @Then("the {nth} labreport has a(n) {string} of {string}")
  public void the_nth_labreport_has_a_x_of_y(
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
      case "status", "start date", "condition", "jurisdiction", "receiveddate", "programarea", "reportingfacility",
           "orderingprovider", "orderingfacility" -> equalTo(
          value);
      default -> hasItem(value);
    };
  }

  private JsonPathResultMatchers matchingPath(final String field, final String position) {
    return switch (field.toLowerCase()) {
      case "reportingfacility" -> jsonPath("$[%s].reportingFacility", position);
      case "receiveddate" -> jsonPath("$[%s].receivedDate", position);
      case "programarea" -> jsonPath("$[%s].programArea", position);
      case "jurisdiction" -> jsonPath("$[%s].jurisdiction", position);
      case "condition" -> jsonPath("$[%s].condition", position);
      case "status" -> jsonPath("$[%s].status", position);
      case "start date" -> jsonPath("$[%s].startedOn", position);
      case "orderingprovider" -> jsonPath("$[%s].orderingProvider.last", position);
      case "orderingfacility" -> jsonPath("$[%s].orderingFacility", position);
      default -> throw new AssertionError("Unexpected Lab Report property %s".formatted(field));
    };
  }
}
