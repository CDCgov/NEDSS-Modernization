package gov.cdc.nbs.questionbank.question.request;

public sealed interface CreateQuestionRequest {
    String codeSet();

    String uniqueId();

    String uniqueName();

    String subgroup();

    String description();

    String label();

    String tooltip();

    Long displayControl();

    ReportingInfo dataMartInfo();

    MessagingInfo messagingInfo();

    String adminComments();

    record Text(
            String codeSet,
            String uniqueId,
            String uniqueName,
            String subgroup,
            String description,
            String label,
            String tooltip,
            Long displayControl,
            ReportingInfo dataMartInfo,
            MessagingInfo messagingInfo,
            String adminComments,
            String mask,
            String fieldLength,
            String defaultValue) implements CreateQuestionRequest {
    }

    public record ReportingInfo(
            String reportLabel,
            String defaultRdbTableName,
            String rdbColumnName,
            String dataMartColumnName) {
    }

    public record MessagingInfo(
            boolean includedInMessage,
            String messageVariableId,
            String labelInMessage,
            String codeSystem,
            boolean requiredInMessage,
            String hl7DataType) {
    }
}
