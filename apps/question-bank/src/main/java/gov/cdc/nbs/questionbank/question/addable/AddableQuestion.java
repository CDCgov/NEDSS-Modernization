package gov.cdc.nbs.questionbank.question.addable;

import io.swagger.annotations.ApiModelProperty;

public record AddableQuestion(
    @ApiModelProperty(required = true) long id,
    String type,
    String status,
    String dataType,
    Boolean allowOtherValues,
    @ApiModelProperty(required = true) String uniqueId,
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
    String adminComments) {

}
