package gov.cdc.nbs.questionbank.question;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.model.Question.TextQuestion;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.CreateTextQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.TextMask;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import org.springframework.test.web.servlet.ResultActions;

public class CreateTextQuestionSteps {
  private final ObjectMapper mapper;
  private final CreateQuestionRequester requester;
  private final Active<ResultActions> response;
  private final ConceptFinder conceptFinder;
  private final QuestionMother mother;
  private CreateTextQuestionRequest textRequest;

  public CreateTextQuestionSteps(
      final CreateQuestionRequester requester,
      final Active<ResultActions> response,
      final ConceptFinder conceptFinder,
      final QuestionMother mother,
      final ObjectMapper mapper) {
    this.requester = requester;
    this.response = response;
    this.conceptFinder = conceptFinder;
    this.mother = mother;
    this.mapper = mapper;
  }

  @Given("I have the following create text question request:")
  public void i_have_create_text_question_request(DataTable dataTable) {
    textRequest = toCreateTextRequest(dataTable);
  }

  @When("I send the create text question request")
  public void send_create_text_question_request() throws Exception {
    response.active(requester.send(textRequest));

    TextQuestion q =
        mapper.readValue(
            response.active().andReturn().getResponse().getContentAsString(), TextQuestion.class);
    mother.addManaged(q.id());
  }

  @Then("the text question is created")
  public void the_text_question_is_create() throws Exception {
    Concept codeSystem =
        conceptFinder
            .find("CODE_SYSTEM", textRequest.getMessagingInfo().codeSystem())
            .orElseThrow();
    response
        .active()
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.mask", equalTo(textRequest.getMask().toString())))
        .andExpect(jsonPath("$.fieldSize", equalTo(textRequest.getFieldLength().toString())))
        .andExpect(jsonPath("$.defaultValue", equalTo(textRequest.getDefaultValue())))
        .andExpect(jsonPath("$.codeSet", equalTo(textRequest.getCodeSet().toString())))
        .andExpect(jsonPath("$.uniqueId", equalTo(textRequest.getUniqueId())))
        .andExpect(jsonPath("$.uniqueName", equalTo(textRequest.getUniqueName())))
        .andExpect(jsonPath("$.status", equalTo("Active")))
        .andExpect(jsonPath("$.subgroup", equalTo(textRequest.getSubgroup())))
        .andExpect(jsonPath("$.description", equalTo(textRequest.getDescription())))
        .andExpect(jsonPath("$.type", equalTo("TEXT")))
        .andExpect(jsonPath("$.label", equalTo(textRequest.getLabel())))
        .andExpect(jsonPath("$.tooltip", equalTo(textRequest.getTooltip())))
        .andExpect(
            jsonPath("$.displayControl", equalTo(textRequest.getDisplayControl().intValue())))
        .andExpect(jsonPath("$.adminComments", equalTo(textRequest.getAdminComments())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.defaultLabelInReport",
                equalTo(textRequest.getDataMartInfo().reportLabel())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.defaultRdbTableName",
                equalTo(textRequest.getDataMartInfo().defaultRdbTableName())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.rdbColumnName",
                equalTo(
                    textRequest.getSubgroup()
                        + "_"
                        + textRequest.getDataMartInfo().rdbColumnName().toUpperCase())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.dataMartColumnName",
                equalTo(textRequest.getDataMartInfo().dataMartColumnName())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.includedInMessage",
                equalTo(textRequest.getMessagingInfo().includedInMessage())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.messageVariableId",
                equalTo(textRequest.getMessagingInfo().messageVariableId())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.labelInMessage",
                equalTo(textRequest.getMessagingInfo().labelInMessage())))
        .andExpect(jsonPath("$.messagingInfo.codeSystem", equalTo(codeSystem.conceptName())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.requiredInMessage",
                equalTo(textRequest.getMessagingInfo().requiredInMessage())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.hl7DataType",
                equalTo(textRequest.getMessagingInfo().hl7DataType())));
  }

  private CreateTextQuestionRequest toCreateTextRequest(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    CreateTextQuestionRequest request = new CreateTextQuestionRequest();
    request.setCodeSet("LOCAL".equals(map.get("codeSet")) ? CodeSet.LOCAL : CodeSet.PHIN);
    request.setUniqueId(map.get("uniqueId"));

    request.setUniqueName(map.get("uniqueName"));
    request.setDescription(map.get("description"));
    request.setMask(TextMask.valueOf(map.get("mask")));
    request.setDisplayControl(Long.valueOf(map.get("displayControl")));
    request.setFieldLength(Integer.valueOf(map.get("fieldLength")));
    request.setDefaultValue(map.get("defaultValue"));
    request.setLabel(map.get("label"));
    request.setTooltip(map.get("tooltip"));
    request.setSubgroup(map.get("subgroup"));
    request.setAdminComments(map.get("adminComments"));

    request.setDataMartInfo(
        new ReportingInfo(
            map.get("reportLabel"),
            map.get("defaultRdbTableName"),
            map.get("rdbColumnName"),
            map.get("dataMartColumnName")));

    if ("false".equals(map.get("includedInMessage"))) {
      request.setMessagingInfo(new MessagingInfo(false, null, null, null, false, null));
    } else {
      request.setMessagingInfo(
          new MessagingInfo(
              "true".equals(map.get("includedInMessage").toLowerCase()),
              map.get("messageVariableId"),
              map.get("labelInMessage"),
              map.get("codeSystem"),
              "true".equals(map.get("requiredInMessage").toLowerCase()),
              map.get("hl7DataType")));
    }
    return request;
  }
}
