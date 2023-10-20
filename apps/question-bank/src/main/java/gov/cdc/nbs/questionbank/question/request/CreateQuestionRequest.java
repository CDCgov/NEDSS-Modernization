package gov.cdc.nbs.questionbank.question.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @Type(value = CreateQuestionRequest.Text.class, name = "TEXT"),
        @Type(value = CreateQuestionRequest.Date.class, name = "DATE"),
        @Type(value = CreateQuestionRequest.Numeric.class, name = "NUMERIC"),
        @Type(value = CreateQuestionRequest.Coded.class, name = "CODED")
})
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

    QuestionType type();

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
            QuestionType type,

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
            QuestionType type,

            // Date specific fields
            String mask,
            boolean allowFutureDates) implements CreateQuestionRequest {

    }

    record Numeric(
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
            QuestionType type,

            // Numeric specific fields
            String mask,
            String fieldLength,
            String defaultValue,
            Long minValue,
            Long maxValue,
            String relatedUnitsLiteral,
            String relatedUnitsValueSet) implements CreateQuestionRequest {

    }
    record Coded(
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
            QuestionType type,

            // Coded specific fields
            Long valueSet,
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

    public enum UnitType {
        CODED,
        LITERAL
    }
}
