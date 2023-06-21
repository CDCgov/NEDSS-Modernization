package gov.cdc.nbs.questionbank.question.command;

import java.time.Instant;

public sealed interface QuestionCommand {
    long userId();

    Instant requestedOn();

    QuestionData questionData();

    public record AddTextQuestion(
            // Text specific fields
            String mask,
            String fieldLength,
            String defaultValue,

            // General Question fields
            QuestionData questionData,

            // Data Mart info
            ReportingData reportingData,

            // Messaging Info
            MessagingData messagingData,

            // Audit info
            long userId,
            Instant requestedOn) implements QuestionCommand {
    }

    public record AddDateQuestion(
            // Date specific fields
            String mask,
            boolean allowFutureDates,

            // General Question fields
            QuestionData questionData,

            // Data Mart info
            ReportingData reportingData,

            // Messaging Info
            MessagingData messagingData,

            // Audit info
            long userId,
            Instant requestedOn) implements QuestionCommand {
    }

    public record AddNumericQuestion(
            // Date specific fields
            String mask,
            String fieldLength,
            String defaultValue,
            Long minValue,
            Long maxValue,
            String unitTypeCd, // Coded or Literal
            String unitValue, // Id of Value set, or literal value

            // General Question fields
            QuestionData questionData,

            // Data Mart info
            ReportingData reportingData,

            // Messaging Info
            MessagingData messagingData,

            // Audit info
            long userId,
            Instant requestedOn) implements QuestionCommand {
    }

    public record AddCodedQuestion(
            // Coded specific fields
            Long valueSet,
            String defaultValue,

            // General Question fields
            QuestionData questionData,

            // Data Mart info
            ReportingData reportingData,

            // Messaging Info
            MessagingData messagingData,

            // Audit info
            long userId,
            Instant requestedOn) implements QuestionCommand {
    }

    record QuestionData(
            String codeSet,
            String localId,
            String uniqueName,
            String subgroup,
            String description,
            String label,
            String tooltip,
            Long displayControl,
            String adminComments,
            QuestionOid questionOid) {
    }

    record ReportingData(
            String reportLabel,
            String defaultRdbTableName,
            String rdbColumnName,
            String dataMartColumnName) {
    }

    record MessagingData(
            boolean includedInMessage,
            String messageVariableId,
            String labelInMessage,
            String codeSystem,
            boolean requiredInMessage,
            String hl7DataType) {
    }

    record QuestionOid(
            String oid,
            String system) {
    }



}
