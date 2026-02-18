package gov.cdc.nbs.questionbank.valueset.concept;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.valueset.model.Concept.Status;
import gov.cdc.nbs.questionbank.valueset.request.UpdateConceptRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import org.springframework.test.web.servlet.ResultActions;

public class UpdateConceptSteps {

  private UpdateConceptRequest request;

  private final UpdateConceptRequester requester;
  private final Active<ResultActions> response;

  public UpdateConceptSteps(
      final UpdateConceptRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @Given("I have the following update concept request:")
  public void i_have_the_update_concept_request(DataTable dataTable) {
    this.request = toUpdateConceptRequest(dataTable);
  }

  @When("I send the update concept request for valueset {string} and local code {string}")
  public void i_send_update_request(String valueset, String conceptCode) throws Exception {
    response.active(requester.send(valueset, conceptCode, request));
  }

  @Then("the concept is updated")
  public void concept_is_updated() throws Exception {
    response
        .active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.codeSetName", equalTo("test_value_set")))
        .andExpect(jsonPath("$.display", equalTo(request.display())))
        .andExpect(jsonPath("$.effectiveFromTime", equalTo(request.effectiveFromTime().toString())))
        .andExpect(jsonPath("$.effectiveToTime", equalTo(request.effectiveToTime().toString())))
        .andExpect(jsonPath("$.status", equalTo(request.status().toString())))
        .andExpect(jsonPath("$.adminComments", equalTo(request.adminComments())))
        .andExpect(jsonPath("$.conceptCode", equalTo(request.conceptCode())))
        .andExpect(jsonPath("$.conceptName", equalTo(request.conceptName())))
        .andExpect(jsonPath("$.preferredConceptName", equalTo(request.preferredConceptName())))
        .andExpect(jsonPath("$.codeSystem", equalTo(request.codeSystem())));
  }

  private UpdateConceptRequest toUpdateConceptRequest(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    return new UpdateConceptRequest(
        map.get("longName"),
        map.get("display"),
        LocalDateTime.parse(map.get("effectiveFromTime")).atZone(ZoneId.of("UTC")).toInstant(),
        LocalDateTime.parse(map.get("effectiveToTime")).atZone(ZoneId.of("UTC")).toInstant(),
        Status.valueOf(map.get("status")),
        map.get("adminComments"),
        map.get("conceptCode"),
        map.get("conceptName"),
        map.get("preferredConceptName"),
        map.get("codeSystem"));
  }
}
