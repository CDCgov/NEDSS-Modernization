package gov.cdc.nbs.questionbank.question.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes({
  @JsonSubTypes.Type(value = Question.TextQuestion.class),
  @JsonSubTypes.Type(value = Question.DateQuestion.class),
  @JsonSubTypes.Type(value = Question.NumericQuestion.class),
  @JsonSubTypes.Type(value = Question.CodedQuestion.class)
})
public sealed interface Question {
  long id();

  String codeSet();

  String uniqueId();

  String uniqueName();

  String status();

  String subgroup();

  String description();

  String type();

  String label();

  String tooltip();

  Long displayControl();

  String adminComments();

  DataMartInfo dataMartInfo();

  MessagingInfo messagingInfo();

  public record TextQuestion(
      long id,
      // Text question specific fields
      String mask,
      String fieldSize,
      String defaultValue,

      // General fields
      String codeSet,
      String uniqueId,
      String uniqueName,
      String status,
      String subgroup,
      String description,
      String type,
      String label,
      String tooltip,
      Long displayControl,
      String adminComments,
      DataMartInfo dataMartInfo,
      MessagingInfo messagingInfo)
      implements Question {}

  public record DateQuestion(
      long id,
      // Date question specific fields
      String mask,
      boolean allowFutureDates,

      // General fields
      String codeSet,
      String uniqueId,
      String uniqueName,
      String status,
      String subgroup,
      String description,
      String type,
      String label,
      String tooltip,
      Long displayControl,
      String adminComments,
      DataMartInfo dataMartInfo,
      MessagingInfo messagingInfo)
      implements Question {}

  public record NumericQuestion(
      long id,
      // Numeric question specific fields
      String mask,
      String fieldLength,
      String defaultValue,
      Long minValue,
      Long maxValue,
      String unitTypeCd, // Coded or Literal
      String unitValue, // Id of Value set, or literal value

      // General fields
      String codeSet,
      String uniqueId,
      String uniqueName,
      String status,
      String subgroup,
      String description,
      String type,
      String label,
      String tooltip,
      Long displayControl,
      String adminComments,
      DataMartInfo dataMartInfo,
      MessagingInfo messagingInfo)
      implements Question {}

  public record CodedQuestion(
      long id,
      // Coded question specific fields
      long valueSet,
      String defaultValue,

      // General fields
      String codeSet,
      String uniqueId,
      String uniqueName,
      String status,
      String subgroup,
      String description,
      String type,
      String label,
      String tooltip,
      Long displayControl,
      String adminComments,
      DataMartInfo dataMartInfo,
      MessagingInfo messagingInfo)
      implements Question {}

  public record DataMartInfo(
      String defaultLabelInReport,
      String defaultRdbTableName,
      String rdbColumnName,
      String dataMartColumnName) {}

  public record MessagingInfo(
      boolean includedInMessage,
      String messageVariableId,
      String labelInMessage,
      String codeSystem,
      boolean requiredInMessage,
      String hl7DataType) {}
}
