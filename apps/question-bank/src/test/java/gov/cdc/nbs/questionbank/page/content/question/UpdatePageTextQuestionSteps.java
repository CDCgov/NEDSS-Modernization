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
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageTextQuestionRequest;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdatePageTextQuestionSteps {

  private final UpdatePageQuestionRequester requester;
  private final Active<ResultActions> response;
  private final PageMother pageMother;
  private UpdatePageTextQuestionRequest request;

  public UpdatePageTextQuestionSteps(
      final UpdatePageQuestionRequester requester,
      final Active<ResultActions> response,
      final PageMother pageMother) {
    this.requester = requester;
    this.response = response;
    this.pageMother = pageMother;
  }

  @Given("I have the following update text question request for a page:")
  public void have_the_update_page_text_request(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    ReportingInfo reportingInfo = new ReportingInfo(
        map.get("reportLabel"),
        map.get("defaultRdbTableName"),
        map.get("rdbColumnName"),
        map.get("dataMartColumnName"));

    MessagingInfo messagingInfo;
    if ("false".equals(map.get("includedInMessage"))) {
      messagingInfo = new MessagingInfo(false, null, null, null, false, null);
    } else {
      messagingInfo = new MessagingInfo(
          "true".equalsIgnoreCase(map.get("includedInMessage")),
          map.get("messageVariableId"),
          map.get("labelInMessage"),
          map.get("codeSystem"),
          "true".equalsIgnoreCase(map.get("requiredInMessage")),
          map.get("hl7DataType"));
    }

    request = new UpdatePageTextQuestionRequest(
        map.get("label"),
        map.get("tooltip"),
        "true".equals(map.get("visible")),
        "true".equals(map.get("enabled")),
        "true".equals(map.get("required")),
        Integer.parseInt(map.get("displayControl")),
        map.get("defaultValue"),
        Integer.valueOf(map.get("fieldLength")),
        reportingInfo,
        messagingInfo,
        map.get("adminComments"));
  }

  @When("I send the update text question request for a page")
  public void send_update_page_text_request() throws Exception {
    WaTemplate page = pageMother.one();
    List<WaUiMetadata> content = pageMother.pageContent();
    long id = content.getLast().getId();
    response.active(requester.send(page.getId(), id, request));
  }

  @Then("the text question is updated for the page")
  public void page_text_is_updated() throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.displayControl", equalTo(request.displayControl())))
        .andExpect(jsonPath("$.fieldLength", equalTo(request.fieldLength())))
        .andExpect(jsonPath("$.defaultValue", equalTo(request.defaultValue())))
        .andExpect(jsonPath("$.label", equalTo(request.label())))
        .andExpect(jsonPath("$.tooltip", equalTo(request.tooltip())))
        .andExpect(jsonPath("$.adminComments", equalTo(request.adminComments())))
        .andExpect(jsonPath("$.visible", equalTo(request.visible())))
        .andExpect(jsonPath("$.enabled", equalTo(request.enabled())))
        .andExpect(jsonPath("$.required", equalTo(request.required())))
        .andExpect(jsonPath("$.dataMartInfo.reportLabel", equalTo(request.dataMartInfo().reportLabel())))
        .andExpect(jsonPath("$.dataMartInfo.dataMartColumnName", equalTo(request.dataMartInfo().dataMartColumnName())))
        .andExpect(jsonPath("$.messagingInfo.includedInMessage", equalTo(request.messagingInfo().includedInMessage())))
        .andExpect(jsonPath("$.messagingInfo.messageVariableId", equalTo(request.messagingInfo().messageVariableId())))
        .andExpect(jsonPath("$.messagingInfo.labelInMessage", equalTo(request.messagingInfo().labelInMessage())))
        .andExpect(jsonPath("$.messagingInfo.codeSystem", equalTo(request.messagingInfo().codeSystem())))
        .andExpect(jsonPath("$.messagingInfo.requiredInMessage", equalTo(request.messagingInfo().requiredInMessage())))
        .andExpect(jsonPath("$.messagingInfo.hl7DataType", equalTo(request.messagingInfo().hl7DataType())));
  }
}
