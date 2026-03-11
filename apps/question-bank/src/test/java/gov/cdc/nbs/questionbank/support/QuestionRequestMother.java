package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.model.Question.*;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.CreateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateTextQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.DateMask;
import gov.cdc.nbs.questionbank.question.request.create.NumericMask;
import gov.cdc.nbs.questionbank.question.request.create.TextMask;
import gov.cdc.nbs.questionbank.question.request.update.*;

public class QuestionRequestMother {

  public static CreateTextQuestionRequest localTextRequest() {
    return textRequest(CodeSet.LOCAL, true);
  }

  public static CreateTextQuestionRequest localTextRequest(boolean includeInMessage) {
    return textRequest(CodeSet.LOCAL, includeInMessage);
  }

  public static CreateTextQuestionRequest phinTextRequest() {
    return textRequest(CodeSet.PHIN, true);
  }

  public static CreateTextQuestionRequest phinTextRequest(boolean includeInMessage) {
    return textRequest(CodeSet.PHIN, includeInMessage);
  }

  public static CreateDateQuestionRequest dateRequest() {
    CreateDateQuestionRequest request = new CreateDateQuestionRequest();
    setSharedFields(request);

    request.setDisplayControl(1008L);
    request.setMask(DateMask.DATE);
    request.setAllowFutureDates(false);
    return request;
  }

  private static void setSharedFields(CreateQuestionRequest request) {
    request.setCodeSet(CodeSet.PHIN);
    request.setUniqueId("Test unique Id");
    request.setUniqueName("TEST UNIQUE NAME");
    request.setSubgroup("TEST_Subgroup");
    request.setDescription("Test description");
    request.setLabel("Test label");
    request.setTooltip("Test tooltip");
    request.setDataMartInfo(reportingInfo());
    request.setMessagingInfo(messagingInfo(false));
  }

  public static CreateNumericQuestionRequest numericRequest() {
    return numericRequest(null, "Some literal value");
  }

  public static CreateNumericQuestionRequest numericRequest(Long valueSet, String literalValue) {
    CreateNumericQuestionRequest request = new CreateNumericQuestionRequest();
    setSharedFields(request);

    request.setMask(NumericMask.NUM);
    request.setFieldLength(3);
    request.setDefaultValue(1l);
    request.setMinValue(0l);
    request.setMaxValue(100l);
    request.setRelatedUnitsLiteral(literalValue);
    request.setRelatedUnitsValueSet(valueSet);
    request.setDisplayControl(1008l);
    return request;
  }

  public static CreateCodedQuestionRequest codedRequest(long valueSet) {
    CreateCodedQuestionRequest request = new CreateCodedQuestionRequest();
    setSharedFields(request);

    request.setDisplayControl(1007l);
    request.setAdminComments("Test admin comments");
    request.setValueSet(valueSet);
    return request;
  }

  public static CreateTextQuestionRequest custom(
      String uniqueName,
      String identifier,
      String dataMartColumnName,
      String rdbTableName,
      String rdbColumnName) {
    CreateTextQuestionRequest request = new CreateTextQuestionRequest();
    setSharedFields(request);
    request.setUniqueId(identifier);
    request.setUniqueName(uniqueName);
    request.setDisplayControl(1008l);
    request.setDataMartInfo(
        reportingInfo("custom label", rdbTableName, rdbColumnName, dataMartColumnName));

    request.setMask(TextMask.TXT);
    request.setFieldLength(50);
    request.setDefaultValue("Test default");
    return request;
  }

  private static CreateTextQuestionRequest textRequest(CodeSet codeSet, boolean includedInMessage) {
    CreateTextQuestionRequest request = new CreateTextQuestionRequest();
    setSharedFields(request);

    request.setCodeSet(codeSet);
    request.setMessagingInfo(messagingInfo(includedInMessage));
    request.setMask(TextMask.TXT);
    request.setFieldLength(50);
    request.setDefaultValue("Test default");
    request.setDisplayControl(1008l);
    return request;
  }

  public static MessagingInfo messagingInfo(boolean includedInMessage) {
    return new MessagingInfo(
        includedInMessage,
        "Message Variable Id",
        "Label in message",
        "ABNORMAL_FLAGS_HL7",
        false,
        "IS");
  }

  public static ReportingInfo reportingInfo() {
    return reportingInfo(
        "default label", "RDB_TABLE_NAME", "RDB_COLUMN_NAME", "DATA_MART_COLUMN_NAME");
  }

  public static ReportingInfo reportingInfo(
      String reportLabel, String rdbTableName, String rdbColumnName, String dataMartColumnName) {
    return new ReportingInfo(reportLabel, rdbTableName, rdbColumnName, dataMartColumnName);
  }

  public static UpdateTextQuestionRequest updateTextQuestionRequest() {
    UpdateTextQuestionRequest updateTextQuestionRequest = new UpdateTextQuestionRequest();
    setCommon(updateTextQuestionRequest);
    updateTextQuestionRequest.setType(UpdateQuestionRequest.DataType.TEXT);
    updateTextQuestionRequest.setMask(TextMask.TXT);
    updateTextQuestionRequest.setFieldLength(10);
    updateTextQuestionRequest.setDefaultValue("default value");
    return updateTextQuestionRequest;
  }

  public static UpdateDateQuestionRequest updateDateQuestionRequest() {
    UpdateDateQuestionRequest updateDateQuestionRequest = new UpdateDateQuestionRequest();
    setCommon(updateDateQuestionRequest);
    updateDateQuestionRequest.setType(UpdateQuestionRequest.DataType.DATE);
    updateDateQuestionRequest.setMask(DateMask.DATE);
    updateDateQuestionRequest.setAllowFutureDates(true);
    return updateDateQuestionRequest;
  }

  public static UpdateCodedQuestionRequest updateCodedQuestionRequest() {
    UpdateCodedQuestionRequest updateCodedQuestionRequest = new UpdateCodedQuestionRequest();
    setCommon(updateCodedQuestionRequest);
    updateCodedQuestionRequest.setType(UpdateQuestionRequest.DataType.CODED);
    updateCodedQuestionRequest.setValueSet(1000l);
    updateCodedQuestionRequest.setDefaultValue("default value");
    return updateCodedQuestionRequest;
  }

  public static UpdateNumericQuestionRequest updateNumericQuestionRequest() {
    UpdateNumericQuestionRequest updateNumericQuestionRequest = new UpdateNumericQuestionRequest();
    setCommon(updateNumericQuestionRequest);
    updateNumericQuestionRequest.setType(UpdateQuestionRequest.DataType.NUMERIC);
    updateNumericQuestionRequest.setMask(NumericMask.NUM);
    updateNumericQuestionRequest.setMinValue(10l);
    updateNumericQuestionRequest.setMaxValue(50l);
    updateNumericQuestionRequest.setDefaultValue(25l);
    updateNumericQuestionRequest.setFieldLength(20);
    updateNumericQuestionRequest.setRelatedUnitsValueSet(null);
    updateNumericQuestionRequest.setRelatedUnitsLiteral("test");
    return updateNumericQuestionRequest;
  }

  private static void setCommon(UpdateQuestionRequest updateQuestionRequest) {
    updateQuestionRequest.setUniqueName("updated unique name");
    updateQuestionRequest.setDescription("updated description");
    updateQuestionRequest.setLabel("updated label");
    updateQuestionRequest.setTooltip("updated tooltip");
    updateQuestionRequest.setDisplayControl(10l);
    updateQuestionRequest.setDataMartInfo(getReportingInfo());
    updateQuestionRequest.setMessagingInfo(getMessagingInfo());
  }

  private static ReportingInfo getReportingInfo() {
    return new ReportingInfo("test", "test", "test", "test");
  }

  private static MessagingInfo getMessagingInfo() {
    return new MessagingInfo(true, "test", "test", "PH_ACCEPTAPPLICATION", true, "test");
  }

  public static CreateTextQuestionRequest localWithUniqueId(String uniqueId) {
    CreateTextQuestionRequest request = new CreateTextQuestionRequest();
    request.setUniqueId(uniqueId);
    request.setCodeSet(CodeSet.LOCAL);
    return request;
  }
}
