package gov.cdc.nbs.questionbank.page.content.question.request;

import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.question.request.create.NumericMask;
import io.swagger.annotations.ApiModelProperty;

public record UpdatePageNumericQuestionRequest(
    // general
    @ApiModelProperty(required = true) String label,
    @ApiModelProperty(required = true) String tooltip,
    @ApiModelProperty(required = true) boolean visible,
    @ApiModelProperty(required = true) boolean enabled,
    @ApiModelProperty(required = true) boolean required,
    long displayControl,
    // numeric specific
    NumericMask mask,
    Integer fieldLength,
    Long defaultValue,
    Long minValue,
    Long maxValue,
    String relatedUnitsLiteral,
    Long relatedUnitsValueSet,
    // reporting
    @ApiModelProperty(required = true) ReportingInfo dataMartInfo,
    // messaging
    @ApiModelProperty(required = true) MessagingInfo messagingInfo,
    // admin
    String adminComments) implements UpdatePageQuestionRequest {

}
