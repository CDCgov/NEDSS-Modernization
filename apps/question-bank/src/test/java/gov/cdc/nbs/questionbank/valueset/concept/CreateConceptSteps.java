package gov.cdc.nbs.questionbank.valueset.concept;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.valueset.model.Concept.Status;
import gov.cdc.nbs.questionbank.valueset.request.CreateConceptRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import org.springframework.test.web.servlet.ResultActions;

public class CreateConceptSteps {

  private final CreateConceptRequester requester;
  private final Active<ResultActions> response;
  private CreateConceptRequest request;

  public CreateConceptSteps(
      final CreateConceptRequester requester, final Active<ResultActions> response) {
    this.requester = requester;
    this.response = response;
  }

  @Given("I have the following create concept request:")
  public void i_have_the_create_concept_request(DataTable dataTable) {
    request = toCreateConceptRequest(dataTable);
  }

  @Given("I send a create concept request for the valueset {string}")
  public void i_send_the_create_concept_request(String valueset) throws Exception {
    response.active(requester.create(valueset, request));
  }

  @Then("the concept is created")
  public void the_concept_is_created() throws Exception {
    response
        .active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.codeSetName", equalTo("test_value_set")))
        .andExpect(jsonPath("$.localCode", equalTo(request.localCode())))
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

  private CreateConceptRequest toCreateConceptRequest(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    return new CreateConceptRequest(
        map.get("localCode"),
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
