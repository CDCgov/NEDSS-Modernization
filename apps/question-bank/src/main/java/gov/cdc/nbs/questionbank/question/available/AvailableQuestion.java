package gov.cdc.nbs.questionbank.question.available;

import io.swagger.v3.oas.annotations.media.Schema;

public record AvailableQuestion(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
    String type,
    String status,
    String dataType,
    Boolean allowOtherValues,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String uniqueId,
    String subgroupId,
    String subgroupName,
    Long valuesetId,
    String valuesetName,
    String uniqueName,
    String description,
    String label,
    String tooltip,
    Long displayControlId,
    String displayControlName,
    String defaultLabelInReport,
    String rdbTableName,
    String rdbColumnName,
    String datamartColumn,
    Boolean includedInMessage,
    String messageVariableId,
    String codeSystemName,
    Boolean requiredInMessage,
    String hl7DataType,
    String hl7Segment,
    String adminComments) {}
