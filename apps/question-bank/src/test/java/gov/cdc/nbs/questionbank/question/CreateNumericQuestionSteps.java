package gov.cdc.nbs.questionbank.question;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.model.Question.NumericQuestion;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.NumericMask;
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

public class CreateNumericQuestionSteps {
  private final ObjectMapper mapper;
  private final QuestionMother mother;
  private final CreateQuestionRequester requester;
  private final Active<ResultActions> response;
  private final ConceptFinder conceptFinder;
  private CreateNumericQuestionRequest numericRequest;

  public CreateNumericQuestionSteps(
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

  @Given("I have the following create numeric question request:")
  public void i_have_create_numeric_question_request(DataTable dataTable) {
    numericRequest = toCreateNumericRequest(dataTable);
  }

  @When("I send the create numeric question request")
  public void send_create_numeric_question_request() throws Exception {
    response.active(requester.send(numericRequest));
    NumericQuestion q =
        mapper.readValue(
            response.active().andReturn().getResponse().getContentAsString(),
            NumericQuestion.class);
    mother.addManaged(q.id());
  }

  @Then("the numeric question is created")
  public void the_numeric_question_is_create() throws Exception {
    Concept codeSystem =
        conceptFinder
            .find("CODE_SYSTEM", numericRequest.getMessagingInfo().codeSystem())
            .orElseThrow();
    response
        .active()
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.mask", equalTo(numericRequest.getMask().toString())))
        .andExpect(jsonPath("$.fieldLength", equalTo(numericRequest.getFieldLength().toString())))
        .andExpect(jsonPath("$.minValue", equalTo(numericRequest.getMinValue().intValue())))
        .andExpect(jsonPath("$.maxValue", equalTo(numericRequest.getMaxValue().intValue())))
        .andExpect(jsonPath("$.defaultValue", equalTo(numericRequest.getDefaultValue().toString())))
        .andExpect(jsonPath("$.unitTypeCd", equalTo("LITERAL")))
        .andExpect(jsonPath("$.unitValue", equalTo(numericRequest.getRelatedUnitsLiteral())))
        .andExpect(jsonPath("$.codeSet", equalTo(numericRequest.getCodeSet().toString())))
        .andExpect(jsonPath("$.uniqueId", equalTo(numericRequest.getUniqueId())))
        .andExpect(jsonPath("$.uniqueName", equalTo(numericRequest.getUniqueName())))
        .andExpect(jsonPath("$.status", equalTo("Active")))
        .andExpect(jsonPath("$.subgroup", equalTo(numericRequest.getSubgroup())))
        .andExpect(jsonPath("$.description", equalTo(numericRequest.getDescription())))
        .andExpect(jsonPath("$.type", equalTo("NUMERIC")))
        .andExpect(jsonPath("$.label", equalTo(numericRequest.getLabel())))
        .andExpect(jsonPath("$.tooltip", equalTo(numericRequest.getTooltip())))
        .andExpect(
            jsonPath("$.displayControl", equalTo(numericRequest.getDisplayControl().intValue())))
        .andExpect(jsonPath("$.adminComments", equalTo(numericRequest.getAdminComments())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.defaultLabelInReport",
                equalTo(numericRequest.getDataMartInfo().reportLabel())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.defaultRdbTableName",
                equalTo(numericRequest.getDataMartInfo().defaultRdbTableName())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.rdbColumnName",
                equalTo(
                    numericRequest.getSubgroup()
                        + "_"
                        + numericRequest.getDataMartInfo().rdbColumnName().toUpperCase())))
        .andExpect(
            jsonPath(
                "$.dataMartInfo.dataMartColumnName",
                equalTo(numericRequest.getDataMartInfo().dataMartColumnName())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.includedInMessage",
                equalTo(numericRequest.getMessagingInfo().includedInMessage())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.messageVariableId",
                equalTo(numericRequest.getMessagingInfo().messageVariableId())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.labelInMessage",
                equalTo(numericRequest.getMessagingInfo().labelInMessage())))
        .andExpect(jsonPath("$.messagingInfo.codeSystem", equalTo(codeSystem.conceptName())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.requiredInMessage",
                equalTo(numericRequest.getMessagingInfo().requiredInMessage())))
        .andExpect(
            jsonPath(
                "$.messagingInfo.hl7DataType",
                equalTo(numericRequest.getMessagingInfo().hl7DataType())));
  }

  private CreateNumericQuestionRequest toCreateNumericRequest(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    CreateNumericQuestionRequest request = new CreateNumericQuestionRequest();
    request.setCodeSet("LOCAL".equals(map.get("codeSet")) ? CodeSet.LOCAL : CodeSet.PHIN);
    request.setUniqueId(map.get("uniqueId"));

    request.setUniqueName(map.get("uniqueName"));
    request.setDescription(map.get("description"));
    request.setDisplayControl(Long.valueOf(map.get("displayControl")));
    request.setLabel(map.get("label"));
    request.setTooltip(map.get("tooltip"));
    request.setSubgroup(map.get("subgroup"));
    request.setAdminComments(map.get("adminComments"));

    request.setMask(NumericMask.valueOf(map.get("mask")));
    request.setFieldLength(Integer.valueOf(map.get("fieldLength")));
    request.setDefaultValue(Long.valueOf(map.get("defaultValue")));
    request.setMinValue(Long.valueOf(map.get("minValue")));
    request.setMaxValue(Long.valueOf(map.get("maxValue")));
    request.setRelatedUnitsLiteral(map.get("relatedUnitsLiteral"));

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
