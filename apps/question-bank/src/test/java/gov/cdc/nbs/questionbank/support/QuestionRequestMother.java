package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.MessagingInfo;

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

    private static CreateQuestionRequest.Text textRequest(String codeSet, boolean includedInMessage) {
        return new CreateQuestionRequest.Text(
                codeSet,
                "Test unique Id",
                "TEST UNIQUE NAME",
                "Test Subgroup",
                "Test description",
                "Test label",
                "Test tooltip",
                1111L,
                reportingInfo(),
                messagingInfo(includedInMessage),
                "Test admin comments",
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
        return new ReportingInfo(
                "default label",
                "default rdb table name",
                "RDB_COLUMN_NAME",
                "DATA_MART_COLUMN_NAME");
    }
}
