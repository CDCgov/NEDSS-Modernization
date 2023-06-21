package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.UnitType;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.QuestionType;

public class QuestionRequestMother {

    public static CreateQuestionRequest.Text localTextRequest() {
        return textRequest("LOCAL", true);
    }

    public static CreateQuestionRequest.Text localTextRequest(boolean includeInMessage) {
        return textRequest("LOCAL", includeInMessage);
    }

    public static CreateQuestionRequest.Text phinTextRequest() {
        return textRequest("PHIN", true);
    }

    public static CreateQuestionRequest.Text phinTextRequest(boolean includeInMessage) {
        return textRequest("PHIN", includeInMessage);
    }

    public static CreateQuestionRequest.Date dateRequest() {
        return new CreateQuestionRequest.Date(
                "PHIN",
                "Test unique Id",
                "TEST UNIQUE NAME",
                "Test_Subgroup",
                "Test description",
                "Test label",
                "Test tooltip",
                1008L,
                reportingInfo(),
                messagingInfo(false),
                "Test admin comments",
                QuestionType.DATE,
                "Mask",
                false);
    }

    public static CreateQuestionRequest.Numeric numericRequest() {
        return numericRequest(UnitType.LITERAL, "Some literal value");
    }

    public static CreateQuestionRequest.Numeric numericRequest(UnitType unitType, String unitValue) {
        return new CreateQuestionRequest.Numeric(
                "PHIN",
                "Test unique Id",
                "TEST UNIQUE NAME",
                "Test_Subgroup",
                "Test description",
                "Test label",
                "Test tooltip",
                1008L,
                reportingInfo(),
                messagingInfo(false),
                "Test admin comments",
                QuestionType.NUMERIC,
                "NUM",
                "3",
                "1",
                0L,
                100L,
                unitType,
                unitValue);
    }

    public static CreateQuestionRequest.Coded codedRequest(long valueSet) {
        return new CreateQuestionRequest.Coded(
                "PHIN",
                "Test unique Id",
                "TEST UNIQUE NAME",
                "Test_Subgroup",
                "Test description",
                "Test label",
                "Test tooltip",
                1008L,
                reportingInfo(),
                messagingInfo(true),
                "Test admin comments",
                QuestionType.CODED,
                valueSet,
                null);
    }

    public static CreateQuestionRequest.Text custom(
            String uniqueName,
            String identifier,
            String dataMartColumnName,
            String rdbTableName,
            String rdbColumnName) {
        return new CreateQuestionRequest.Text(
                "PHIN",
                identifier,
                uniqueName,
                "Test_Subgroup",
                "Test description",
                "Test label",
                "Test tooltip",
                1111L,
                reportingInfo("custom label", rdbTableName, rdbColumnName, dataMartColumnName),
                messagingInfo(false),
                "Test admin comments",
                QuestionType.TEXT,
                "Mask",
                "50",
                "Test default");
    }

    private static CreateQuestionRequest.Text textRequest(String codeSet, boolean includedInMessage) {
        return new CreateQuestionRequest.Text(
                codeSet,
                "Test unique Id",
                "TEST UNIQUE NAME",
                "Test_Subgroup",
                "Test description",
                "Test label",
                "Test tooltip",
                1111L,
                reportingInfo(),
                messagingInfo(includedInMessage),
                "Test admin comments",
                QuestionType.TEXT,
                "Mask",
                "50",
                "Test default");
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
}
