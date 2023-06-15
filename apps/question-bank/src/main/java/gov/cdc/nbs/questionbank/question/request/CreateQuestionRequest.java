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
            // General question fields
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

            // Text specific fields
            String mask,
            String fieldLength,
            String defaultValue) implements CreateQuestionRequest {
    }

    record Date(
            // General question fields
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

            // Date specific fields
            String mask,
            boolean allowFutureDates) implements CreateQuestionRequest {

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
