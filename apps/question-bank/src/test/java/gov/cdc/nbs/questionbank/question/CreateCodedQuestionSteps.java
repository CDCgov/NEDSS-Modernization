package gov.cdc.nbs.questionbank.question;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.model.Question.CodedQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.CreateCodedQuestionRequest;
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

public class CreateCodedQuestionSteps {
  private final ObjectMapper mapper;
  private final QuestionMother mother;
  private final CreateQuestionRequester requester;
  private final Active<ResultActions> response;
  private final ConceptFinder conceptFinder;
  private CreateCodedQuestionRequest codedRequest;

  public CreateCodedQuestionSteps(
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

  @Given("I have the following create coded question request:")
  public void i_have_create_coded_question_request(DataTable dataTable) {
    codedRequest = toCreateCodedRequest(dataTable);
  }

  @When("I send the create coded question request")
  public void send_create_coded_question_request() throws Exception {
    response.active(requester.send(codedRequest));

    CodedQuestion q =
        mapper.readValue(
            response.active().andReturn().getResponse().getContentAsString(), CodedQuestion.class);
    mother.addManaged(q.id());
  }

  @Then("the coded question is created")
  public void the_coded_question_is_create() throws Exception {
    Concept codeSystem =
        conceptFinder
            .find("CODE_SYSTEM", codedRequest.getMessagingInfo().codeSystem())
            .orElseThrow();
    response
        .active()
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.valueSet", equalTo(codedRequest.getValueSet().intValue())))
        .andExpect(jsonPath("$.codeSet", equalTo(codedRequest.getCodeSet().toString())))
        .andExpect(jsonPath("$.uniqueId", equalTo(codedRequest.getUniqueId())))
        .andExpect(jsonPath("$.uniqueName", equalTo(codedRequest.getUniqueName())))
        .andExpect(jsonPath("$.status", equalTo("Active")))
        .andExpect(jsonPath("$.subgroup", equalTo(codedRequest.getSubgroup())))
        .andExpect(jsonPath("$.description", equalTo(codedRequest.getDescription())))
        .andExpect(jsonPath("$.type", equalTo("CODED")))
        .andExpect(jsonPath("$.label", equalTo(codedRequest.getLabel())))
        .andExpect(jsonPath("$.tooltip", equalTo(codedRequest.getTooltip())))
        .andExpect(
            jsonPath("$.displayControl", equalTo(codedRequest.getDisplayControl().intValue())))
        .andExpect(jsonPath("$.adminComments", equalTo(codedRequest.getAdminComments())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.defaultLabelInReport",
                equalTo(codedRequest.getDataMartInfo().reportLabel())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.defaultRdbTableName",
                equalTo(codedRequest.getDataMartInfo().defaultRdbTableName())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.rdbColumnName",
                equalTo(
                    codedRequest.getSubgroup()
                        + "_"
                        + codedRequest.getDataMartInfo().rdbColumnName().toUpperCase())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.dataMartColumnName",
                equalTo(codedRequest.getDataMartInfo().dataMartColumnName())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.includedInMessage",
                equalTo(codedRequest.getMessagingInfo().includedInMessage())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.messageVariableId",
                equalTo(codedRequest.getMessagingInfo().messageVariableId())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.labelInMessage",
                equalTo(codedRequest.getMessagingInfo().labelInMessage())))
        .andExpect(jsonPath("$.messagingInfo.codeSystem", equalTo(codeSystem.conceptName())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.requiredInMessage",
                equalTo(codedRequest.getMessagingInfo().requiredInMessage())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.hl7DataType",
                equalTo(codedRequest.getMessagingInfo().hl7DataType())));
  }

  private CreateCodedQuestionRequest toCreateCodedRequest(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    CreateCodedQuestionRequest request = new CreateCodedQuestionRequest();
    request.setCodeSet("LOCAL".equals(map.get("codeSet")) ? CodeSet.LOCAL : CodeSet.PHIN);
    request.setUniqueId(map.get("uniqueId"));

    request.setUniqueName(map.get("uniqueName"));
    request.setDescription(map.get("description"));
    request.setDisplayControl(Long.valueOf(map.get("displayControl")));
    request.setLabel(map.get("label"));
    request.setTooltip(map.get("tooltip"));
    request.setSubgroup(map.get("subgroup"));
    request.setAdminComments(map.get("adminComments"));

    request.setValueSet(Long.valueOf(map.get("valueSet")));

    request.setDataMartInfo(
        new ReportingInfo(
            map.get("reportLabel"),
            map.get("defaultRdbTableName"),
            map.get("rdbColumnName"),
            map.get("dataMartColumnName")));

    if ("true".equals(map.get("includedInMessage").toLowerCase())) {
      request.setMessagingInfo(
          new MessagingInfo(
              "true".equals(map.get("includedInMessage").toLowerCase()),
              map.get("messageVariableId"),
              map.get("labelInMessage"),
              map.get("codeSystem"),
              "true".equals(map.get("requiredInMessage").toLowerCase()),
              map.get("hl7DataType")));
    } else {
      request.setMessagingInfo(new MessagingInfo(false, null, null, null, false, null));
    }
    return request;
  }
}
