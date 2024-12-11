package gov.cdc.nbs.questionbank.page.content.question;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.Map;
import org.springframework.test.web.servlet.ResultActions;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageCodedQuestionValuesetRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdatePageCodedQuestionValuesetSteps {
  private final UpdatePageQuestionValuesetRequester requester;
  private final Active<ResultActions> response;
  private final PageMother pageMother;
  private UpdatePageCodedQuestionValuesetRequest request;

  public UpdatePageCodedQuestionValuesetSteps(
      final UpdatePageQuestionValuesetRequester requester,
      final Active<ResultActions> response,
      final PageMother pageMother) {
    this.requester = requester;
    this.response = response;
    this.pageMother = pageMother;
  }

  @Given("I have the following update question valueset request for a page:")
  public void have_the_update_page_coded_valueset_request(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();

    request = new UpdatePageCodedQuestionValuesetRequest(Long.parseLong(map.get("valueset")));
  }


  @When("I send the update coded question valueset request for a page")
  public void send_update_page_coded_valueset_request() throws Exception {
    WaTemplate page = pageMother.one();
    List<WaUiMetadata> content = pageMother.pageContent();
    long id = content.getLast().getId();
    response.active(requester.send(page.getId(), id, request));
  }

  @Then("the coded question valueset is updated for the page")
  public void page_coded_is_updated() throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valueSet", equalTo(request.valueset())));
  }

}
