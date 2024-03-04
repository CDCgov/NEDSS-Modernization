package gov.cdc.nbs.questionbank.page.content.question.model;

import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import io.swagger.annotations.ApiModelProperty;

public record EditableQuestion(
    // general
    @ApiModelProperty(required = true) CodeSet codeSet,
    @ApiModelProperty(required = true) String uniqueName,
    @ApiModelProperty(required = true) String uniqueId,
    @ApiModelProperty(required = true) String subgroup,
    @ApiModelProperty(required = true) String description,
    @ApiModelProperty(required = true) String label,
    @ApiModelProperty(required = true) String tooltip,
    @ApiModelProperty(required = true) long displayControl,
    @ApiModelProperty(required = true) boolean visible,
    @ApiModelProperty(required = true) boolean enabled,
    @ApiModelProperty(required = true) boolean required,
    String adminComments,
    // type specific
    String questionType,
    String defaultValue,
    Integer fieldLength,
    String mask,
    boolean allowFutureDates,
    Long valueSet,
    Long minValue,
    Long maxValue,
    String relatedUnitsLiteral,
    Long relatedUnitsValueSet,
    // reporting
    @ApiModelProperty(required = true) ReportingInfo dataMartInfo,
    // messaging
    @ApiModelProperty(required = true) MessagingInfo messagingInfo) {


}
