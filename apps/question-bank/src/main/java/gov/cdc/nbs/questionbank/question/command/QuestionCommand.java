package gov.cdc.nbs.questionbank.question.command;

import java.time.Instant;

public sealed interface QuestionCommand {
    long userId();

    Instant requestedOn();

    String localId();

    String uniqueName();

    String subgroup();

    String description();

    String label();

    String tooltip();

    String codeSet();

    Long displayControl();

    String adminComments();

    QuestionOid questionOid();

    public record AddTextQuestion(
            // Text specific fields
            String mask,
            String fieldLength,
            String defaultValue,

            // General Question fields
            String codeSet,
            String localId,
            String uniqueName,
            String subgroup,
            String description,
            String label,
            String tooltip,
            Long displayControl,
            String adminComments,
            QuestionOid questionOid,

            // Data Mart info
            ReportingData reportingData,

            // Messaging Info
            MessagingData messagingData,

            // Audit info
            long userId,
            Instant requestedOn) implements QuestionCommand {
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
