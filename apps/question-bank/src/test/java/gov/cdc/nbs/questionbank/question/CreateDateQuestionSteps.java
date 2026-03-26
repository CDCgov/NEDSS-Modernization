package gov.cdc.nbs.questionbank.question;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.model.Question.DateQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.DateMask;
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

public class CreateDateQuestionSteps {
  private final ObjectMapper mapper;
  private final QuestionMother mother;
  private final CreateQuestionRequester requester;
  private final Active<ResultActions> response;
  private final ConceptFinder conceptFinder;
  private CreateDateQuestionRequest dateRequest;

  public CreateDateQuestionSteps(
      final CreateQuestionRequester requester,
      final Active<ResultActions> response,
      final ConceptFinder conceptFinder,
      final ObjectMapper mapper,
      final QuestionMother mother) {
    this.requester = requester;
    this.response = response;
    this.conceptFinder = conceptFinder;
    this.mapper = mapper;
    this.mother = mother;
  }

  @Given("I have the following create date question request:")
  public void i_have_create_date_question_request(DataTable dataTable) {
    dateRequest = toCreateDateRequest(dataTable);
  }

  @When("I send the create date question request")
  public void send_create_date_question_request() throws Exception {
    response.active(requester.send(dateRequest));
    String content = response.active().andReturn().getResponse().getContentAsString();
    DateQuestion q = mapper.readValue(content, DateQuestion.class);
    mother.addManaged(q.id());
  }

  @Then("the date question is created")
  public void the_date_question_is_create() throws Exception {
    Concept codeSystem =
        conceptFinder
            .find("CODE_SYSTEM", dateRequest.getMessagingInfo().codeSystem())
            .orElseThrow();
    response
        .active()
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.mask", equalTo(dateRequest.getMask().toString())))
        .andExpect(jsonPath("$.allowFutureDates", equalTo(dateRequest.isAllowFutureDates())))
        .andExpect(jsonPath("$.codeSet", equalTo(dateRequest.getCodeSet().toString())))
        .andExpect(jsonPath("$.uniqueId", equalTo(dateRequest.getUniqueId())))
        .andExpect(jsonPath("$.uniqueName", equalTo(dateRequest.getUniqueName())))
        .andExpect(jsonPath("$.status", equalTo("Active")))
        .andExpect(jsonPath("$.subgroup", equalTo(dateRequest.getSubgroup())))
        .andExpect(jsonPath("$.description", equalTo(dateRequest.getDescription())))
        .andExpect(jsonPath("$.type", equalTo("DATE")))
        .andExpect(jsonPath("$.label", equalTo(dateRequest.getLabel())))
        .andExpect(jsonPath("$.tooltip", equalTo(dateRequest.getTooltip())))
        .andExpect(
            jsonPath("$.displayControl", equalTo(dateRequest.getDisplayControl().intValue())))
        .andExpect(jsonPath("$.adminComments", equalTo(dateRequest.getAdminComments())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.defaultLabelInReport",
                equalTo(dateRequest.getDataMartInfo().reportLabel())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.defaultRdbTableName",
                equalTo(dateRequest.getDataMartInfo().defaultRdbTableName())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.rdbColumnName",
                equalTo(
                    dateRequest.getSubgroup()
                        + "_"
                        + dateRequest.getDataMartInfo().rdbColumnName().toUpperCase())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.dataMartColumnName",
                equalTo(dateRequest.getDataMartInfo().dataMartColumnName())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.includedInMessage",
                equalTo(dateRequest.getMessagingInfo().includedInMessage())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.messageVariableId",
                equalTo(dateRequest.getMessagingInfo().messageVariableId())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.labelInMessage",
                equalTo(dateRequest.getMessagingInfo().labelInMessage())))
        .andExpect(jsonPath("$.messagingInfo.codeSystem", equalTo(codeSystem.conceptName())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.requiredInMessage",
                equalTo(dateRequest.getMessagingInfo().requiredInMessage())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.hl7DataType",
                equalTo(dateRequest.getMessagingInfo().hl7DataType())));
  }

  private CreateDateQuestionRequest toCreateDateRequest(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    CreateDateQuestionRequest request = new CreateDateQuestionRequest();
    request.setCodeSet("LOCAL".equals(map.get("codeSet")) ? CodeSet.LOCAL : CodeSet.PHIN);
    request.setUniqueId(map.get("uniqueId"));

    request.setUniqueName(map.get("uniqueName"));
    request.setDescription(map.get("description"));
    request.setDisplayControl(Long.valueOf(map.get("displayControl")));
    request.setLabel(map.get("label"));
    request.setTooltip(map.get("tooltip"));
    request.setSubgroup(map.get("subgroup"));
    request.setAdminComments(map.get("adminComments"));

    request.setMask(DateMask.valueOf(map.get("mask")));
    request.setAllowFutureDates("true".equals(map.get("allowFutureDates").toLowerCase()));

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
