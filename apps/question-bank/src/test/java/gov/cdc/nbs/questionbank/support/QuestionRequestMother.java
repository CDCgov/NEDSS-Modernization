package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.entity.question.UnitType;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateTextQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestionRequest.QuestionType;
import gov.cdc.nbs.questionbank.question.request.CreateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.MessagingInfo;

public class QuestionRequestMother {

    public static CreateTextQuestionRequest localTextRequest() {
        return textRequest("LOCAL", true);
    }

    public static CreateTextQuestionRequest localTextRequest(boolean includeInMessage) {
        return textRequest("LOCAL", includeInMessage);
    }

    public static CreateTextQuestionRequest phinTextRequest() {
        return textRequest("PHIN", true);
    }

    public static CreateTextQuestionRequest phinTextRequest(boolean includeInMessage) {
        return textRequest("PHIN", includeInMessage);
    }

    public static CreateDateQuestionRequest dateRequest() {
        CreateDateQuestionRequest request = new CreateDateQuestionRequest();
        setSharedFields(request);

        request.setDisplayControl(1008L);
        request.setMask("Mask");
        request.setAllowFutureDates(false);
        return request;
    }

    private static void setSharedFields(CreateQuestionRequest request) {
        request.setCodeSet("PHIN");
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

        request.setMask("NUM");
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
        request.setDataMartInfo(reportingInfo(
                "custom label",
                rdbTableName,
                rdbColumnName,
                dataMartColumnName));

        request.setMask("Mask");
        request.setFieldLength(50);
        request.setDefaultValue("Test default");
        return request;
    }

    private static CreateTextQuestionRequest textRequest(String codeSet, boolean includedInMessage) {
        CreateTextQuestionRequest request = new CreateTextQuestionRequest();
        setSharedFields(request);

        request.setCodeSet(codeSet);
        request.setMessagingInfo(
                messagingInfo(includedInMessage));
        request.setMask("Mask");
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
                "default label",
                "RDB_TABLE_NAME",
                "RDB_COLUMN_NAME",
                "DATA_MART_COLUMN_NAME");
    }

    public static ReportingInfo reportingInfo(String reportLabel, String rdbTableName, String rdbColumnName,
            String dataMartColumnName) {
        return new ReportingInfo(
                reportLabel,
                rdbTableName,
                rdbColumnName,
                dataMartColumnName);
    }

    public static UpdateQuestionRequest update(QuestionType type) {
        return new UpdateQuestionRequest(
                "updated unique name",
                "updated description",
                type,
                "updated label",
                "updated tooltip",
                123L,
                "updated admin comments",
                "updated default value",
                "TXT_SSN",
                "12",
                false,
                -1L,
                70L,
                UnitType.LITERAL,
                "some literalValue",
                333L,
                "updated report label",
                "UP_DMART_COL",
                "UP_RDB_COL_NM",
                true,
                "Updated msg var id",
                "updated msg label",
                "PH_ACCEPTAPPLICATION",
                false,
                "CWE");
    }

    public static UpdateQuestionRequest update() {
        return update(QuestionType.TEXT);
    }

    public static CreateTextQuestionRequest localWithUniqueId(String uniqueId) {
        CreateTextQuestionRequest request = new CreateTextQuestionRequest();
        request.setUniqueId(uniqueId);
        request.setCodeSet("LOCAL");
        return request;
    }

}
