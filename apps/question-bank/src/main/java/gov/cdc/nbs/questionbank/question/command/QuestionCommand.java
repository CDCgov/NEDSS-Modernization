package gov.cdc.nbs.questionbank.question.command;

import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.request.create.DateMask;
import gov.cdc.nbs.questionbank.question.request.create.NumericMask;
import gov.cdc.nbs.questionbank.question.request.create.TextMask;
import java.time.Instant;

public sealed interface QuestionCommand {
  long userId();

  Instant requestedOn();

  public sealed interface CreateQuestionCommand extends QuestionCommand {
    QuestionData questionData();

    ReportingData reportingData();

    MessagingData messagingData();
  }

  public record Update(
      UpdatableQuestionData questionData,
      String defaultValue,
      String mask,
      String fieldLength,
      boolean allowFutureDates,
      Long valueSet,
      Long minValue,
      Long maxValue,
      String relatedUnitsLiteral,
      Long relatedUnitsValueSet,
      ReportingData reportingData,
      MessagingData messagingData,
      long userId,
      Instant requestedOn)
      implements QuestionCommand {}

  public record AddTextQuestion(
      // Text specific fields
      TextMask mask,
      Integer fieldLength,
      String defaultValue,

      // General Question fields
      QuestionData questionData,

      // Data Mart info
      ReportingData reportingData,

      // Messaging Info
      MessagingData messagingData,

      // Audit info
      long userId,
      Instant requestedOn)
      implements CreateQuestionCommand {}

  public record AddDateQuestion(
      // Date specific fields
      DateMask mask,
      boolean allowFutureDates,

      // General Question fields
      QuestionData questionData,

      // Data Mart info
      ReportingData reportingData,

      // Messaging Info
      MessagingData messagingData,

      // Audit info
      long userId,
      Instant requestedOn)
      implements CreateQuestionCommand {}

  public record AddNumericQuestion(
      // Date specific fields
      NumericMask mask,
      Integer fieldLength,
      Long defaultValue,
      Long minValue,
      Long maxValue,

      // Related units
      String relatedUnitsLiteral,
      Long relatedUnitsValueSet,

      // General Question fields
      QuestionData questionData,

      // Data Mart info
      ReportingData reportingData,

      // Messaging Info
      MessagingData messagingData,

      // Audit info
      long userId,
      Instant requestedOn)
      implements CreateQuestionCommand {}

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
      Instant requestedOn)
      implements CreateQuestionCommand {}

  record QuestionData(
      CodeSet codeSet,
      String localId,
      String uniqueName,
      String subgroup,
      String description,
      String label,
      String tooltip,
      Long displayControl,
      String adminComments,
      QuestionOid questionOid) {}

  record UpdatableQuestionData(
      boolean questionInUse,
      String uniqueName,
      String description,
      String label,
      String tooltip,
      Long displayControl,
      String adminComments,
      QuestionOid questionOid) {}

  record ReportingData(
      String reportLabel,
      String defaultRdbTableName,
      String rdbColumnName,
      String dataMartColumnName) {}

  record MessagingData(
      boolean includedInMessage,
      String messageVariableId,
      String labelInMessage,
      String codeSystem,
      boolean requiredInMessage,
      String hl7DataType) {}

  record QuestionOid(String oid, String system) {}

  public record SetStatus(boolean active, long userId, Instant requestedOn)
      implements QuestionCommand {}
}
